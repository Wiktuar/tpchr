package ru.tpchr.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tpchr.DTO.CommentDTO;
import ru.tpchr.entities.Comment;

import java.util.List;

@Repository
public interface CommentRepo extends CrudRepository<Comment, Long> {
//  мметод получения комментрия по его ID
    Comment getCommentById(long id);

//  метод получения списка комментариев, относящихся к одному стихотворению
    @Query("SELECT c FROM Comment c WHERE c.poemId = :poemId")
    List<Comment> getCommentsByPoemId(@Param("poemId") long poemId);

//  метод получения CommentDTO по ID комментария
    @Query("SELECT new ru.tpchr.DTO.CommentDTO(c.id, c.text) FROM Comment c WHERE c.id = :id")
    CommentDTO getTextCommentById(@Param("id") long id);

//  мтод получения ID сохраненного комментария по ID его автора и метке времени
    @Query("select c.id from Comment c where c.author.id =:authorId and c.timeStamp = :timeStamp")
    long getCommentID(@Param("authorId") long authorId,
                      @Param("timeStamp") String timeStamp);

//  метод получения количества комментариев у стихотворения
    @Query("select count(c) from Comment c where c.poemId = :id")
    String getCountOfCommentsById(@Param("id") long id);

}
