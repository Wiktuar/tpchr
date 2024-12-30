package ru.tpchr.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditPoemDTO {
    private long id;
    private String header;
    private String content;
    private String fileName;
    private String releaseDate;

    public EditPoemDTO(long id, String header, String fileName, String releaseDate) {
        this.id = id;
        this.header = header;
        this.fileName = fileName;
        this.releaseDate = releaseDate;
    }
}
