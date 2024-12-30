//https://sysout.ru/preobrazovanie-entity-v-dto-s-pomoshhyu-modelmapper/
package ru.tpchr.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tpchr.DTO.AuthorCabinetDTO;
import ru.tpchr.DTO.AuthorDTO;
import ru.tpchr.DTO.CommentDTO;
import ru.tpchr.DTO.EditAlbumDTO;
import ru.tpchr.entities.Comment;
import ru.tpchr.entities.music.Album;
import ru.tpchr.entities.security.Author;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ConvertEntityToDTO {

    private static ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        ConvertEntityToDTO.modelMapper = modelMapper;
    }

//  метод, преобразующий Comment в CommentDTO
    public CommentDTO convertToCommentDTO(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }

//  метод, преобразующий Author в AuthorDTO
    public AuthorDTO convertToAuthorDto(Author author) {
        return modelMapper.map(author, AuthorDTO.class);
    }

//  метод, преобразующий Author в AuthorCabinetDTO
    public static AuthorCabinetDTO convertToAuthorCabinetDto(Author author) {
        return modelMapper.map(author, AuthorCabinetDTO.class);
    }

//  метод, который будет использоваться в преобразовании списка записей
    public CommentDTO convertToCommentDtoForList(Comment comment, String email, boolean deleteAll) {
        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
        commentDTO.setAuthorDTO(convertToAuthorDto(comment.getAuthor()));
        if(deleteAll) {
            commentDTO.setDeleted(true);
            if(comment.getAuthor().getEmail().equals(email)){
                commentDTO.setUpdated(true);
            }
        } else {
            if(comment.getAuthor().getEmail().equals(email)){
                commentDTO.setUpdated(true);
                commentDTO.setDeleted(true);
            }
        }

        return commentDTO;
    }

    //  метод, преобразующий Author в AuthorCabinetDTO
    public static EditAlbumDTO convertToEditAlbumDTO(Album album) {
        return modelMapper.map(album, EditAlbumDTO.class);
    }


    //  метод преобразующий список сущностей в DTO. Он принимает список и функцию для конвертации.
    public <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(e -> converter.apply(e)).collect(Collectors.toList());
    }
}
