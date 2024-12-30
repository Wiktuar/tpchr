package ru.tpchr.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tpchr.DTO.EditPoemDTO;
import ru.tpchr.DTO.LikesPoemDto;
import ru.tpchr.entities.poem.Poem;

import java.util.List;
import java.util.Set;

@Repository
public interface PoemRepo extends CrudRepository<Poem, Long> {

//  это альтернативный вариант. Можно доставать всего автора, а можно просто его ID
//    Set<Poem> getAllByAuthor(Author author);
    @Query("SELECT p FROM Poem p WHERE p.author.email = :email ORDER BY p.releaseDate DESC")
    List<Poem> getAllPoemsByAuthorEmail(@Param("email") String email);

//  получение PoemDTO для редактирования стизотворения на фронтенде
    @Query("select new ru.tpchr.DTO.EditPoemDTO(p.id, p.header, p.fileName, p.releaseDate) from Poem p WHERE p.id = :id")
    EditPoemDTO getEditPoemDTO(@Param("id") Long id);

//  получение PoemDTO вместе с количеством лайков и комментариев.
//  определение лайкнул ли пользователь стихотворение или нет.
    @Query("select new ru.tpchr.DTO.LikesPoemDto(p.id, p.header,p.fileName, p.releaseDate, p.likes.size, p.comments.size, " +
            "sum(case when pl.email = :email then 1 else 0 end) > 0 ) " +
            "from Poem p left join p.likes pl group by p having p.id = :id" )
    LikesPoemDto getPoemWithLikesAndComments(@Param("email") String email, @Param("id") long id);

//    @Query("from Poem p join fetch p.likes pl join fetch p.comments pc where p.author.id = :id" )
//    List<Poem> getListPoemsWithLikesAndComments(@Param("email1") String email1,
//                                                                    @Param("email2") String email2);

//  получение относительного пути файла картинки для стиховторения (нужно для удаления)
    @Query("select p.fileName from Poem p where p.id = :id")
    String getPoemFileName(@Param("id") long id);

//  получения списка имен всех файлов картинок стихотворения перед удалением
    @Query("select p.fileName from Poem p")
    Set<String> getAllPoemFileNames();

//  получение LikesPoemDto для конкретного пользователя по его почте
    @Query("select new ru.tpchr.DTO.LikesPoemDto(p.id, p.header,p.fileName, p.releaseDate, p.poemPreview, p.author.email, p.author.firstName, p.author.lastName, p.author.pathToAvatar, size(p.likes) , size(p.comments) , " +
            "sum(case when pl.email = :email then 1 else 0 end) > 0 ) " +
            "from Poem p left join p.likes pl group by p having p.author.email = :em")
    List<LikesPoemDto> getPoemsByUser(@Param("email") String email, @Param("em") String em);

    //  получение LikesPoemDto для конкретного пользователя по его ID
    @Query("select new ru.tpchr.DTO.LikesPoemDto(p.id, p.header,p.fileName, p.releaseDate, p.poemPreview, p.author.email, p.author.firstName, p.author.lastName, p.author.pathToAvatar, size(p.likes) , size(p.comments) , " +
            "sum(case when pl.email = :email then 1 else 0 end) > 0, p.author.id ) " +
            "from Poem p left join p.likes pl group by p having p.author.id = :id")
    List<LikesPoemDto> getPoemsByAuthorID(@Param("email") String email, @Param("id") long id);

//  получение всех стихов всех авторов
    @Query("select new ru.tpchr.DTO.LikesPoemDto(p.id, p.header,p.fileName, p.releaseDate, p.poemPreview, p.author.email, p.author.firstName, p.author.lastName, p.author.pathToAvatar, size(p.likes) , size(p.comments) , " +
            "sum(case when pl.email = :email then 1 else 0 end) > 0 ) " +
            "from Poem p left join p.likes pl group by p")
    List<LikesPoemDto> getAllPoem(@Param("email") String email);

    //  метод проверяет, создавал ли пользователь стих с таким названием или еще нет
    @Query("SELECT p.id FROM Poem p WHERE p.header = :header AND p.author.email = :email")
    String getPoemId(@Param("header") String header, @Param("email") String email);
}

