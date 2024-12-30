package ru.tpchr.DTO;

import lombok.Getter;
import lombok.Setter;
import ru.tpchr.entities.music.Song;

@Getter
@Setter
public class LikesAlbumDto extends CompositionDTO {
    private String songPreview;
    private Song song;

    public LikesAlbumDto(long id, String header, String fileName, String releaseDate, String songPreview,
                         String email, String firstName, String lastName, String pathToAvatar,
                         int likes, int comments, boolean meLiked, long albumAuthorId) {
        super(id, header, fileName, releaseDate, email, firstName, lastName, pathToAvatar, likes, comments, meLiked, albumAuthorId);
        this.songPreview = songPreview;
    }

//  в этом конструкторе нет поля albumAuthorId. Этот конструктор используется там
//  где LikesAlbumDto получаются через principal.petName() по почте пользователя
    public LikesAlbumDto(long id, String header, String fileName, String releaseDate, String songPreview,
                         String email, String firstName, String lastName, String pathToAvatar,
                         Song song, int likes, int comments, boolean meLiked) {
        super(id, header, fileName, releaseDate, email, firstName, lastName, pathToAvatar, likes, comments, meLiked);
        this.songPreview = songPreview;
        this.song = song;
    }

    public LikesAlbumDto(long id, String header, String fileName, String releaseDate, String songPreview,
                         String email, String firstName, String lastName, String pathToAvatar,
                         Song song, int likes, int comments, boolean meLiked, long albumAuthorId) {
        super(id, header, fileName, releaseDate, email, firstName, lastName, pathToAvatar, likes, comments, meLiked, albumAuthorId);
        this.songPreview = songPreview;
        this.song = song;
    }

    public LikesAlbumDto(long id, String header, String fileName, String releaseDate, int likes, int comments, boolean meLiked) {
        super(id, header, fileName, releaseDate, likes, comments, meLiked);
    }
}
