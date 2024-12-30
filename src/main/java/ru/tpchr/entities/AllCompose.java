package ru.tpchr.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tpchr.entities.security.Author;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "compositions")
public class AllCompose {
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
    @Column(name = "text_preview")
    private String textPreview;
    @Column(name = "link_preview")
    private String linkPreview;
//  поскольку данное поле находится под управлением hibernate, то его необходимо сделать неизменяемым
    @Column(name = "comp_type", insertable = false, updatable = false )
    private int compType;
}