package ru.tpchr.entities.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.tpchr.entities.Comment;
import ru.tpchr.entities.poem.Content;
import ru.tpchr.entities.poem.Poem;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //general fields
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "activation_code")
    private String activationCode;

    @Column(name = "path_to_Avatar")
    private String pathToAvatar;

    @JsonIgnore
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Poem> poems;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Content> contents;

    @JsonIgnore
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Comment> comments;

//  Если тип каскадирования стоит PERSIST или ALL, то получим ошибку
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "authors_roles",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @JsonIgnore
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @JsonIgnore
    @Column(name = "block")
    @Enumerated(value = EnumType.STRING)
    private Block block;

    @Column(name = "description")
    private String description;

    //  social nets
    private String vk;
    private String tg;
    private String yt;
    private String rt;

    public Author() {

    }

    public Author(String firstName, String lastName, String pathToAvatar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pathToAvatar = pathToAvatar;
    }

    public boolean isActive() {
        return getStatus().equals(Status.ACTIVE);
    }

    public boolean isBlock(){
        return getBlock().equals(Block.NO_BANNED);
    }


    //добавление и удаление роли
    public void addRole(Role role){
        this.roles.add(role);
        role.getAuthors().add(this);
    }

    public void removeRole(Role role){
        this.roles.remove(role);
        role.getAuthors().remove(this);
    }

//  методы, связывающие Author и Poem. При двухсторонней связи считается хорошей практикой
    public void addPoem(Poem poem) {
        poems.add(poem);
        poem.setAuthor(this);
    }

    public void removeComment(Poem poem) {
        poems.remove(poem);
        poem.setAuthor(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id.equals(author.id) && firstName.equals(author.firstName) && lastName.equals(author.lastName) && email.equals(author.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email);
    }
}
