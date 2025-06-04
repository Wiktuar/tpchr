package ru.tpchr.repository.music;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tpchr.DTO.LikesAlbumDto;
import ru.tpchr.entities.music.Album;

import java.util.List;
import java.util.Set;

@Repository
public interface AlbumRepo extends CrudRepository<Album, Long> {

    @Override
    void deleteById(Long id);

//  метод получения имени файла музыкального альбома
    @Query("select a.fileName from Album a where a.id = :id")
    String getAlbumFileName(@Param("id") long id);


//  метод, возвращающий альбом и все его песни для последующего редактирования в виде DTO
    @Query("SELECT a FROM Album a join fetch a.songs WHERE a.id = :id")
    Album getAlbumWithSongs(@Param("id") long id);


//  метод проверяет, создавал ли пользователь альбом с таким названием или еще нет
    @Query("SELECT a.id FROM Album a WHERE a.header = :header AND a.author.email = :email")
    String getAlbumId(@Param("header") String header, @Param("email") String email);

//  получение всех LikesAlbumDto для конкретного пользователя
    @Query("select new ru.tpchr.DTO.LikesAlbumDto(a.id, a.header,a.fileName, a.releaseDate, a.songPreview, a.author.email, a.author.firstName, a.author.lastName, a.author.pathToAvatar, (select s from Song s where s.urlToMusicFile = a.songPreview), size(a.likes) , size(a.comments) , " +
            "sum(case when al.email = :email then 1 else 0 end) > 0 ) " +
            "from Album a left join a.likes al group by a having a.author.email = :email")
    List<LikesAlbumDto> getAlbumsByUser(@Param("email") String email);

    //  получение конкретного LikesAlbumDto по ID альбома
    @Query("select new ru.tpchr.DTO.LikesAlbumDto(a.id, a.header,a.fileName, a.releaseDate, a.songPreview, a.author.email, a.author.firstName, a.author.lastName, a.author.pathToAvatar, (select s from Song s where s.urlToMusicFile = a.songPreview), size(a.likes) , size(a.comments) , " +
            "sum(case when al.email = :email then 1 else 0 end) > 0 ) " +
            "from Album a left join a.likes al group by a having a.id = :id")
    LikesAlbumDto getLikesAlbumDtoById(@Param("email") String email, @Param("id") long id);

    //  получение LikesAlbumDTO по его ID вместе с количеством лайков и комментариев.
    //  определение лайкнул ли пользователь стихотворение или нет.
    @Query("select new ru.tpchr.DTO.LikesAlbumDto(a.id, a.header, a.fileName, a.releaseDate, size(a.likes) , size(a.comments) , " +
            "sum(case when al.email = :email then 1 else 0 end) > 0 ) " +
            "from Album a left join a.likes al group by a having a.id = :id" )
    LikesAlbumDto getAlbumDto(@Param("email") String email, @Param("id") long id);

    //  получение LikesAlbumDto для конкретного пользователя по его ID
    @Query("select new ru.tpchr.DTO.LikesAlbumDto(a.id, a.header, a.fileName, a.releaseDate, a.songPreview, a.author.email, a.author.firstName, a.author.lastName, a.author.pathToAvatar, (select s from Song s where s.urlToMusicFile = a.songPreview), size(a.likes) , size(a.comments) , " +
            "sum(case when al.email = :email then 1 else 0 end) > 0, a.author.id) " +
            "from Album a left join a.likes al group by a having a.author.id = :id")
    List<LikesAlbumDto> getAlumsByAuthorID(@Param("email") String email, @Param("id") long id);
}
