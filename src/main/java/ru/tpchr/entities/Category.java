package ru.tpchr.entities;

import lombok.Data;
import ru.tpchr.entities.poem.Poem;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Set;

//@Entity
@Data
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String categoryName;

//    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    private Set<Poem> poems ;
}
