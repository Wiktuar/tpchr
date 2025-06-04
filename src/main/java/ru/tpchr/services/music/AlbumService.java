package ru.tpchr.services.music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import ru.tpchr.DTO.CompositionDTO;
import ru.tpchr.DTO.LikesAlbumDto;
import ru.tpchr.entities.music.Album;
import ru.tpchr.repository.CompositionRepo;
import ru.tpchr.repository.music.AlbumRepo;
import ru.tpchr.utils.Utils;

import java.io.File;
import java.util.List;
import java.util.Set;


@Service
public class AlbumService {

    @Value("${upload.path}")
    private String uploadPath;

    private CompositionRepo compositionRepo;
    private AlbumRepo albumRepo;

    @Autowired
    public void setAlbumRepo(AlbumRepo albumRepo) {
        this.albumRepo = albumRepo;
    }

    @Autowired
    public void setCompositionRepo(CompositionRepo compositionRepo) {
        this.compositionRepo = compositionRepo;
    }

    //  метод, сохраняющий альбом и его песни
    @Transactional
    public Album saveAlbum(Album album){
       return albumRepo.save(album);
    }

//  метод, возвращающий все альбоы одного пользователя с превью-песней, лайками и комментариями
    @Transactional
    public List<? extends CompositionDTO> getAlbumsByUser(String email){
        return Utils.sortCompositionList(albumRepo.getAlbumsByUser(email));
    }

//  получение конкретного LikesAlbumDto по ID альбома
    public LikesAlbumDto getLikesAlbumDto(String email, long id){
        return albumRepo.getLikesAlbumDtoById(email, id);
    }

//  метод проверки существует ли музыкальный альбом с таким названием или нет
    public boolean checkAlbumNotExists(String header, String email){
        return albumRepo.getAlbumId(header, email) == null;
    }

//  метод, возвращающий альбом и все его песни для последующего редактирования в виде DTO
    public Album getAlbumWithSongs(long id){
        return albumRepo.getAlbumWithSongs(id);
    }

//  метод получение LikesAlbumDto для конкретного пользователя по его ID
    public List<? extends CompositionDTO> getAlbumsByUserID(String email, Long id){
        return Utils.sortCompositionList(albumRepo.getAlumsByAuthorID(email, id));
    }

    //  метод удаления альбома  по его ID
    public void deleteAlbumById(long id){
        String filename = albumRepo.getAlbumFileName(id);
        String pathToAlbumDirectory = filename.substring(0, filename.lastIndexOf("/"));
        String deletePath = uploadPath + pathToAlbumDirectory;
        boolean result = FileSystemUtils.deleteRecursively(new File(deletePath));
        if(result) albumRepo.deleteById(id);
    }
}
