package ru.tpchr.controller.REST;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tpchr.DTO.CommentDTO;
import ru.tpchr.entities.Comment;
import ru.tpchr.entities.security.Author;
import ru.tpchr.services.AuthorService;
import ru.tpchr.services.CommentService;
import ru.tpchr.utils.ConvertEntityToDTO;
import ru.tpchr.utils.Utils;

import java.security.Principal;
import java.util.List;

@RestController
public class CommentsController {
    private CommentService commentService;
    private AuthorService authorService;
    private ConvertEntityToDTO convertEntityToDTO;

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setConvertEntityToDTO(ConvertEntityToDTO convertEntityToDTO) {
        this.convertEntityToDTO = convertEntityToDTO;
    }

    //  метод получения всех комментариев
    @GetMapping("/getComments/{id}")
    public List<CommentDTO> getAllComments(@RequestHeader("referer") String referer,
                                           @PathVariable long id, Principal principal){
        boolean deleteeAll = referer.contains("cabinet");
        List<Comment> comments = commentService.getListOfCommentsByPoemId(id);
        String email = (principal != null) ? principal.getName() : "email";
        return convertEntityToDTO.convertList(comments, c -> convertEntityToDTO.convertToCommentDtoForList(c, email, deleteeAll));
    }

    //  метод сохранения комментария в БД
    @PostMapping("/addComment")
    public CommentDTO addComment(@ModelAttribute Comment comment,
                                 Principal principal){
        comment.setTimeStamp(Utils.convertTimeToString());
        Author author = authorService.getAuthorByEmail(principal.getName());

        String[] massOfLines = comment.getText().split("\\n");;
        comment.setText(Utils.addBrTag(massOfLines));
        comment.setAuthor(author);
        commentService.saveComment(comment);
        comment.setId(commentService.getCommentId(comment.getAuthor().getId(), comment.getTimeStamp()));;
        CommentDTO commentDTO = convertEntityToDTO.convertToCommentDTO(comment);
        commentDTO.setAuthorDTO(convertEntityToDTO.convertToAuthorDto(author));
        commentDTO.setCountOfComments(Integer.parseInt(commentService.getCountOfCommentsById(comment.getPoemId())));
        commentDTO.setUpdated(true);
        commentDTO.setDeleted(true);
        return commentDTO;
    }

    //  метод удаления комментария по ID
    @DeleteMapping("/deletecomment/{id}/{poemId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable long id,
                                                    @PathVariable long poemId){
        commentService.deleteCommentById(id);
        String count = commentService.getCountOfCommentsById(poemId);

        return new ResponseEntity<String>(count, HttpStatus.OK);
    }

    @PostMapping("/editcomment/{id}")
    public CommentDTO updateComment(@RequestParam long id,
                                    @RequestParam String text){
        String[] massOfLines = text.split("\\n");;
        Comment comment = commentService.getCommentById(id);
        comment.setText(Utils.addBrTag(massOfLines));
        comment = commentService.saveComment(comment);
        CommentDTO commentDTO = convertEntityToDTO.convertToCommentDTO(comment);
        commentDTO.setAuthorDTO(convertEntityToDTO.convertToAuthorDto(comment.getAuthor()));
        return commentDTO;
    }

    //  метод получения комментария в виде CommentDTO
    @GetMapping("/getcomment/{id}")
    public CommentDTO getCommentById(@PathVariable long id) throws JsonProcessingException {
        CommentDTO commentDTO = commentService.getTextCommentById(id);
        commentDTO.setText(Utils.removeBrTag(commentDTO.getText()));
        return commentDTO;
    }
}
