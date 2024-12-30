package ru.tpchr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tpchr.DTO.CommentDTO;
import ru.tpchr.entities.Comment;
import ru.tpchr.repository.CommentRepo;

import java.util.List;

@Service
public class CommentService {

    private CommentRepo commentRepo;

    @Autowired
    public void setCommentRepo(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

//  метод сохранения комментария в базу данных.
    public Comment saveComment(Comment comment){
        return commentRepo.save(comment);
    }

//  метод получения ID комментария из базы данных по метке времени и ID его автора
    public long getCommentId(Long authorId, String timeStamp){
        return commentRepo.getCommentID(authorId, timeStamp);
    }

    public Comment getCommentById(long id){
        return commentRepo.getCommentById(id);
    }

//  метод получения CommentDTO по ID комментария
    public CommentDTO getTextCommentById(long id){
        return commentRepo.getTextCommentById(id);
    }

//  удаление комментария о ID
    public void deleteCommentById(Long id){
        commentRepo.deleteById(id);
    }

//  метод получения комментариев по значению poemId
    public List<Comment> getListOfCommentsByPoemId(long poemId){
        return commentRepo.getCommentsByPoemId(poemId);
    }

//  метод получения количества комментариев для конкретного стихотворения
    public String getCountOfCommentsById(long id){
        return commentRepo.getCountOfCommentsById(id);
    }
}
