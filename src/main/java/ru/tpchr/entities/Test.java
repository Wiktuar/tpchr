package ru.tpchr.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "test")
public class Test {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String header;
    private String text;
}
