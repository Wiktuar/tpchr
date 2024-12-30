package ru.tpchr.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorCabinetDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String pathToAvatar;
    private String description;
    private String vk;
    private String tg;
    private String yt;
    private String rt;

    public AuthorCabinetDTO(long id, String firstName, String lastName, String pathToAvatar, String description, String vk, String tg, String yt, String rt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pathToAvatar = pathToAvatar;
        this.description = description;
        this.vk = vk;
        this.tg = tg;
        this.yt = yt;
        this.rt = rt;
    }
}
