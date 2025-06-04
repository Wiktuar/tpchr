package ru.tpchr.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikesPoemDto extends CompositionDTO {

    private String poemPreview;
    private String content;

    public LikesPoemDto(long id, String header, String fileName, String releaseDate, String poemPreview,
                        String email, String firstName, String lastname, String pathToAvatar,
                        int likes, int comments, boolean meLiked) {
        super(id, header, fileName, releaseDate, email, firstName, lastname, pathToAvatar, likes, comments, meLiked);
        this.poemPreview = poemPreview;
    }

    public LikesPoemDto(long id, String header, String fileName, String releaseDate, String poemPreview,
                        String email, String firstName, String lastname, String pathToAvatar,
                        int likes, int comments, boolean meLiked, long poemAuthorId) {
        super(id, header, fileName, releaseDate, email, firstName, lastname, pathToAvatar, likes, comments, meLiked, poemAuthorId);
        this.poemPreview = poemPreview;
    }

    public LikesPoemDto(long id, String header, String fileName, String releaseDate, String email, String firstName, String lastName, String pathToAvatar, int likes, int comments, boolean meLiked) {
        super(id, header, fileName, releaseDate, email, firstName, lastName, pathToAvatar, likes, comments, meLiked);
    }
}
