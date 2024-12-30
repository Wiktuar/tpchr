package ru.tpchr.DTO;

import lombok.Getter;
import lombok.Setter;
import ru.tpchr.entities.music.Song;

import java.util.List;

@Getter
@Setter
public class EditAlbumDTO {
    private long id;
    private String header;
    List<Song> songs;
    private String releaseDate;
    private String fileName;

    public EditAlbumDTO() { }

    public EditAlbumDTO(long id, String header, List<Song> songs, String releaseDate, String fileName) {
        this.id = id;
        this.header = header;
        this.songs = songs;
        this.releaseDate = releaseDate;
        this.fileName = fileName;
    }
}
