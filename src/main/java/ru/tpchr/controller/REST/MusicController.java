package ru.tpchr.controller.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tpchr.DTO.CompositionDTO;
import ru.tpchr.DTO.EditAlbumDTO;
import ru.tpchr.DTO.LikesAlbumDto;
import ru.tpchr.entities.music.Album;
import ru.tpchr.entities.music.Song;
import ru.tpchr.exceptions.ComposeExistsException;
import ru.tpchr.services.AuthorService;
import ru.tpchr.services.music.AlbumService;
import ru.tpchr.services.music.SongService;
import ru.tpchr.utils.ConvertEntityToDTO;
import ru.tpchr.utils.Utils;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
public class MusicController {
    @Value("${upload.path}")
    private String uploadPath;

    @Value("${album.cover}")
    private String albumCoverPath;

    private AuthorService authorService;
    private AlbumService albumService;
    private SongService songService;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setAlbumService(AlbumService albumService) {
        this.albumService = albumService;
    }

    @Autowired
    public void setSongService(SongService songService) {
        this.songService = songService;
    }

    //  метод, сохраняющий альбом и песни
    @PostMapping("/savemusic")
    public HttpStatus saveMusic(@RequestParam("id") long id,
                                @RequestParam("album_header") String albumHeader,
                                @RequestParam("old_album_header") String oldAlbumHeader,
                                @RequestParam("releaseDate") String releaseDate,
                                @RequestParam("cover") String coverImage,
                                @RequestParam("songId") long[] ides,
                                @RequestParam("song_url") String[] oldSongUrls,
                                @RequestParam("header") String[] headers,
                                @RequestParam("duration") String[] durations,
                                @RequestParam("file") MultipartFile[] files,
                                Principal principal) throws IOException {
        for(String h : headers){
            System.out.println("Заголовок песни: " + h);
        }


        if(id == 0){
            if(!albumService.checkAlbumNotExists(albumHeader, principal.getName())) throw new ComposeExistsException();
        }

        Album album = new Album();
        if(id != 0) {
            album.setId(id);
            album.setReleaseDate(releaseDate);
        } else{
            album.setReleaseDate(Utils.convertTimeToString());
        }
        album.setHeader(albumHeader);
        album.setAuthor(authorService.getAuthorByEmail(principal.getName()));

        Path uploadFolder = Paths.get(uploadPath + principal.getName() + "/music/" + albumHeader);
        System.out.println("Путь альбома: " + uploadFolder);

        if(!oldAlbumHeader.isEmpty() && !oldAlbumHeader.equals(albumHeader)){
            boolean result = new File(uploadPath + principal.getName() + "/music/" + oldAlbumHeader)
                    .renameTo(new File(uploadFolder.toString()));
            if (!result) System.out.println("Не удалось переименовать файл");
        }


        if(!Files.exists(uploadFolder)){
            try {
                Files.createDirectories(uploadFolder);
            } catch (IOException e) {
                System.out.println("Не удается создать альбом " + uploadFolder.toString());
            }
        }

        if(coverImage.equals("defaultCover.png")){
            try {
                Files.copy(Paths.get(albumCoverPath), Paths.get(uploadFolder.toString() + "/defaultCover.jpg"));
            } catch (IOException e) {
                System.out.println("Не удается скопировать дефолтную обложку");
            }
            album.setFileName(principal.getName() + "/music/" + albumHeader + "/defaultCover.jpg");
        } else if (coverImage.contains("/music/") && coverImage.contains("defaultCover.jpg")){
            album.setFileName(principal.getName() + "/music/" + albumHeader + "/defaultCover.jpg");
        } else if (coverImage.contains("/music/") && coverImage.contains("albumCover.jpg")){
            album.setFileName(principal.getName() + "/music/" + albumHeader + "/albumCover.jpg");
        }
        else {
            Utils.saveCircumcisedImage(uploadFolder.toString(), coverImage, "/albumCover.jpg");
            album.setFileName(principal.getName() + "/music/" + albumHeader + "/albumCover.jpg");
        }


        for (int i = 0; i < headers.length; i++) {
            if(oldSongUrls[i].isEmpty() && headers[i].isEmpty())continue;

            if(!oldSongUrls[i].isEmpty() &&  headers[i].isEmpty()){
                songService.deleteSongByUrlToMusicFile(oldSongUrls[i]);
                Files.deleteIfExists(Paths.get(uploadPath + oldSongUrls[i]));
                continue;
            }


            Song song = new Song();
            if(ides[i] != 0) song.setId(ides[i]);
            song.setHeader(headers[i]);

//         если тип файла text/plain, значит файл остался прежний, максимум, он был переименован
            if(files[i].getContentType().equals("text/plain")){
//             если альбом не был переименован
                if(albumHeader.equals(oldAlbumHeader)){
                    song.setUrlToMusicFile(files[i].getOriginalFilename());
                    song.setDuration(durations[i]);
                }

//             если альбом был переименован
                if(!oldAlbumHeader.isEmpty() && !oldAlbumHeader.equals(albumHeader)){
                    String fileHeader = Objects.requireNonNull(files[i].getOriginalFilename())
                            .substring(Objects.requireNonNull(files[i].getOriginalFilename()).lastIndexOf("/") + 1);
                    song.setUrlToMusicFile(principal.getName() + "/music/" + albumHeader + "/" + fileHeader);
                    song.setDuration(durations[i]);
                }
//               если была изменена песня
            } else {
                if(!oldSongUrls[i].isEmpty()) {
                    Files.deleteIfExists(Paths.get(uploadPath + oldSongUrls[i]));
                }
                files[i].transferTo(new File(uploadPath + principal.getName() + "/music/" + albumHeader + "/" + files[i].getOriginalFilename()));
                  song.setUrlToMusicFile(principal.getName() + "/music/" + albumHeader + "/" + files[i].getOriginalFilename());
                song.setDuration(durations[i]);
                try {
                    song.setDuration(Utils.getMusicFileDuration(uploadPath + principal.getName() + "/music/" + albumHeader + "/" + files[i].getOriginalFilename()));
                } catch (UnsupportedAudioFileException e) {
                    System.out.println("Can not define duration of music file");
                }
            }

            if(Objects.isNull(album.getSongPreview())){
                album.setSongPreview(song.getUrlToMusicFile());
            }
            System.out.println("Cсылка на песню " + song.getUrlToMusicFile());
            album.addSong(song);
        }

        Album savedAlbum = albumService.saveAlbum(album);

        if (savedAlbum != null)  return HttpStatus.OK;
        else return HttpStatus.BAD_REQUEST;
    }

    //  метод, возвращающий альбом со всеми песнями
    @GetMapping("/main/songs/{id}")
    public Set<Song> getSongsByAlbumId(@PathVariable long id){
        return songService.getAllSongsByAlbumId(id);
    }

    //  метод получения альбома по его ID
    @GetMapping("/cabinet/update/album/{id}")
    public EditAlbumDTO getAlbumWithSongs(@PathVariable long id){
        Album album = albumService.getAlbumWithSongs(id);
        return ConvertEntityToDTO.convertToEditAlbumDTO(album);
    }

    //  метод, возвращающий превью музыкальных альбомов для конкретного автора
    @GetMapping("/authors/{id}/albums")
    public ResponseEntity<List<? extends CompositionDTO>> getAlbumsByAuthorId(
            @PathVariable long id,
            Principal principal){
        String principalName = "";
        principalName = (Objects.isNull(principal)) ? "default" : principal.getName();
        List<? extends CompositionDTO> lpd =  albumService.getAlbumsByUserID(principalName, id);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(lpd.size()))
                .body(lpd);
    }

//  метод, возвращающий размер переданного файла
    @PostMapping("/main/filesize")
    public String getFileSize(@RequestParam("filePath") String filePath){
        File file = new File(uploadPath + filePath);
        long fileSize = file.length();
        return String.valueOf(fileSize);
    }
}
