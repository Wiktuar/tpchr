package ru.tpchr.repository.music;

import org.springframework.data.repository.CrudRepository;
import ru.tpchr.entities.music.Song;

import java.util.Set;

public interface SongRepo extends CrudRepository<Song, Long> {
    //метод получения песен по ID альбома
    Set<Song> getAllByAlbumId(Long id);
}
