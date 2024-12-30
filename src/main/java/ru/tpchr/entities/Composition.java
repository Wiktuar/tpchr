package ru.tpchr.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tpchr.entities.security.Author;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="comp_type",   discriminatorType = DiscriminatorType.INTEGER)
@Table(name = "compositions")
@Entity
public class Composition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "header")
    private String header;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "file_name")
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH
    )
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "comp_id")
//  поле comp.id у комментария должно позволять устанавливать значение в NULL
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "comps_likes",
            joinColumns = @JoinColumn(name = "comp_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> likes = new HashSet<>();

    //  equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Composition comp = (Composition) o;
        return id == comp.id && header.equals(comp.header) && releaseDate.equals(comp.releaseDate) && fileName.equals(comp.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, header, releaseDate, fileName);
    }

}
