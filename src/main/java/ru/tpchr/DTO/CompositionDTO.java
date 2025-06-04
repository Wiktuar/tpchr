package ru.tpchr.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//класс наследуется сущностями DTO непосредственно произведений, к примеру LikesPoemDTO
@AllArgsConstructor
@Getter
@Setter
public class CompositionDTO {
    private long id;
    private String header;
    private String fileName;
    private String releaseDate;
    private String email;
    private String firstName;
    private String lastName;
    private String pathToAvatar;
    private int likes;
    private int comments;
    private boolean meLiked;
    private long compAuthorID;

    public CompositionDTO(long id, String header, String fileName, String releaseDate, String email, String firstName, String lastName, String pathToAvatar, int likes, int comments, boolean meLiked) {
        this.id = id;
        this.header = header;
        this.fileName = fileName;
        this.releaseDate = releaseDate;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pathToAvatar = pathToAvatar;
        this.likes = likes;
        this.comments = comments;
        this.meLiked = meLiked;
    }

    //  этот вариант конструктора может пригодиться для личного кабинета автора. где в превью авторские поля не нужны.
    public CompositionDTO(long id, String header, String fileName, String releaseDate, int likes, int comments, boolean meLiked) {
        this.id = id;
        this.header = header;
        this.fileName = fileName;
        this.releaseDate = releaseDate;
        this.likes = likes;
        this.comments = comments;
        this.meLiked = meLiked;
    }
}
