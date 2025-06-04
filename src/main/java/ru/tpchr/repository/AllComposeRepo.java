package ru.tpchr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tpchr.DTO.AllComposeDTO;
import ru.tpchr.entities.AllCompose;

import java.util.List;

@Repository
public interface AllComposeRepo extends JpaRepository<AllCompose, Long> {
    @Query(" select new ru.tpchr.DTO.AllComposeDTO(ac.id, ac.header, ac.fileName, ac.releaseDate, ac.author.email, ac.author.firstName, ac.author.lastName, ac.author.pathToAvatar, ac.textPreview, ac.linkPreview, size(ac.likes), size(ac.comments), case when ac.compType = 2 then (select s from Song s where s.urlToMusicFile = ac.linkPreview) end, ac.compType, " +
            "sum(case when acl.email = :email then 1 else 0 end) > 0) from  AllCompose ac left join ac.likes acl group by ac")
    List<AllComposeDTO> getAllComposeDto(@Param("email") String email);

    //  получение всех произведений конкретного пользователя
    @Query(" select new ru.tpchr.DTO.AllComposeDTO(ac.id, ac.header, ac.fileName, ac.releaseDate, ac.author.email, ac.author.firstName, ac.author.lastName, ac.author.pathToAvatar, ac.textPreview, ac.linkPreview, size(ac.likes), size(ac.comments), case when ac.compType = 2 then (select s from Song s where s.urlToMusicFile = ac.linkPreview) end, ac.compType, " +
            "sum(case when acl.email = :email then 1 else 0 end) > 0, ac.author.id) from  AllCompose ac left join ac.likes acl group by ac having ac.author.id = :id")
    List<AllComposeDTO> getAllComposeDtoByAuthorId(@Param("email") String email, @Param("id") long id);


    @Override
    List<AllCompose> findAll();
}
