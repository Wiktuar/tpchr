package ru.tpchr.entities.music;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tpchr.entities.Composition;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("2")
public class Album extends Composition {

    @Column(name = "link_preview")
    public String songPreview;

    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Song> songs = new ArrayList<>();

    public void addSong(Song song) {
        songs.add(song);
        song.setAlbum(this);
    }

    public void removeSong(Song song) {
        songs.remove(song);
        song.setAlbum(null);
    }
}
