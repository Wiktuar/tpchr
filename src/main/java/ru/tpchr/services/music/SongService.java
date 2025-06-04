package ru.tpchr.services.music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tpchr.entities.music.Song;
import ru.tpchr.repository.music.SongRepo;

import java.util.Set;

@Service
public class SongService {

    private SongRepo songRepo;

    @Autowired
    public void setAlbumRepo(SongRepo songRepo) {
        this.songRepo = songRepo;
    }

    public Set<Song> getAllSongsByAlbumId(long id){
        return songRepo.getAllByAlbumId(id);
    }

    //  метод удаления песни по url
    public void deleteSongByUrlToMusicFile(String url){
        Song song = songRepo.getSongByUrlToMusicFile(url);
        songRepo.delete(song);
    }
}
