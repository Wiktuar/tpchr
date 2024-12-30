package ru.tpchr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tpchr.DTO.CompositionDTO;
import ru.tpchr.entities.Composition;

import java.util.List;

@Repository
public interface CompositionRepo extends JpaRepository<Composition, Long> {

    //  метод, возвращающий список авторов, поставивших лайк произведению.
    @Query("from Composition c left join fetch c.likes where c.id = :id")
    Composition getListOfLikes(@Param("id") long id);

    @Override
    List<Composition> findAll();

    List<Composition> findAllByAuthorId(long id);

    @Query("select new ru.tpchr.DTO.CompositionDTO(c.id, c.header, c.fileName, c.releaseDate, c.author.email, c.author.firstName, " +
            "c.author.lastName, c.author.pathToAvatar, size(c.likes) , size(c.comments) , " +
            "sum(case when cl.email = :email then 1 else 0 end) > 0 ) " +
            "from Composition c left join c.likes cl group by c having c.id = :id" )
    CompositionDTO getCompositionDTO(@Param("email") String email, @Param("id") long id);
}
