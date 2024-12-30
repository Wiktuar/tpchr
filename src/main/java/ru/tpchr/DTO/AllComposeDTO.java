package ru.tpchr.DTO;

import lombok.Getter;
import lombok.Setter;
import ru.tpchr.entities.music.Song;

@Getter
@Setter
public class AllComposeDTO extends CompositionDTO{

    private String textPreview;
    private String linkPreview;
    private Song song;
    private int compType;

    public AllComposeDTO(long id, String header, String fileName, String releaseDate, String email, String firstName, String lastName, String pathToAvatar, String textPreview, String linkPreview, int likes, int comments, Song song, int compType, boolean meLiked) {
          super(id, header, fileName, releaseDate, email, firstName, lastName, pathToAvatar, likes, comments, meLiked);
        this.textPreview = textPreview;
        this.linkPreview = linkPreview;
        this.song = song;
        this.compType = compType;
    }

//    public AllComposeDTO(long id, String header, String fileName, String releaseDate, String email, String firstName, String lastName, String pathToAvatar, String textPreview, String linkPreview, int likes, int comments, int compType, boolean meLiked) {
//        super(id, header, fileName, releaseDate, email, firstName, lastName, pathToAvatar, likes, comments, meLiked);
//        this.textPreview = textPreview;
//        this.linkPreview = linkPreview;
//        this.compType = compType;
//    }
}
