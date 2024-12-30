package ru.tpchr.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String pathToAvatar;

    public AuthorDTO(String firstName, String lastName, String pathToAvatar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pathToAvatar = pathToAvatar;
    }
}
