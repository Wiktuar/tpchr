package ru.tpchr.controller.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tpchr.DTO.CompositionDTO;
import ru.tpchr.DTO.EditPoemDTO;
import ru.tpchr.DTO.LikesPoemDto;
import ru.tpchr.entities.poem.Content;
import ru.tpchr.entities.poem.Poem;
import ru.tpchr.entities.security.Author;
import ru.tpchr.exceptions.ComposeExistsException;
import ru.tpchr.services.AuthorService;
import ru.tpchr.services.ContentService;
import ru.tpchr.services.PoemService;
import ru.tpchr.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RestController
public class PoemController {
    @Value("${upload.path}")
    private String uploadPath;

    @Value("${poem.cover}")
    private String defaultCoverPath;

    private AuthorService authorService;
    private PoemService poemService;
    private ContentService contentService;

    @Autowired
    public void setPoemService(PoemService poemService) {
        this.poemService = poemService;
    }

    @Autowired
    public void setContentService(ContentService contentService) {
        this.contentService = contentService;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }


    //  метод, добавляющий стихотворение в базу данных.
    @PostMapping("/cabinet/poems")
    public HttpStatus savePoem(@ModelAttribute Poem poem,
                               @RequestParam("content") String poemContent,
                               @RequestParam("cover") String coverImage,
                               @RequestParam("oldFileName") String oldFileName,
                               Principal principal) throws IOException {

        if(poem.getId() == 0){
            if(!poemService.checkPoemNotExists(poem.getHeader(), principal.getName())) throw new ComposeExistsException();
        }

        Content content = new Content();

        File uploadFolder = new File(uploadPath + principal.getName() + "/poems/");
        if(!uploadFolder.exists()){
            uploadFolder.mkdirs();
        }

        if(coverImage.equals("poemCover.jpg")){
            System.out.println("дефолтный путь картинки " + defaultCoverPath);
            if(! new File(uploadFolder.toString() + "/poemCover.jpg").exists()){
                Files.copy(Paths.get(defaultCoverPath), Paths.get(uploadFolder.toString() + "/poemCover.jpg"));
            }
            poem.setFileName(principal.getName() + "/poems/" +  "poemCover.jpg");
        } else {
            if(!oldFileName.isEmpty() && !oldFileName.contains("poemCover.jpg")){
                Files.delete(Paths.get(uploadPath + oldFileName));
            }
            Utils.saveCircumcisedImage(uploadFolder.toString(), coverImage,  "/" + poem.getHeader() + ".jpg");
            poem.setFileName(principal.getName() + "/poems/" + poem.getHeader() + ".jpg");
        }

        String[] massOfLines = poemContent.split("\\n");
        content.setContent(Utils.addBrTag(massOfLines));
        poem.setPoemPreview(Utils.getPoemPreview(massOfLines));
        if(poem.getReleaseDate().isEmpty()){
            poem.setReleaseDate(Utils.convertTimeToString());
        }
        Author author = authorService.getAuthorByEmail(principal.getName());
        poem.setAuthor(author);
        content.setAuthor(author);

//   данная проверка делается, чтобы избежать ошибки
//   detached entity passed to persist, связанной с проблемой предсуществования ID
//   e сущности Poem при ее обновлении
        if(poem.getId() != 0){
            content.setId(poem.getId());
            contentService.savePoemInDB(content);
            poemService.savePoem(poem);
        } else {
//  если же ID == 0, значит мы сохраняем новую сущность
            content.setPoem(poem);
            contentService.savePoemInDB(content);
        }

        return HttpStatus.OK;
    }


    @GetMapping("/cabinet/updatete/poem/{id}")
    public EditPoemDTO getPoemById(@PathVariable long id){
        EditPoemDTO editPoemDTO = poemService.getEditPoemDTO(id);
        String content = contentService.findById(id).getContent();
        editPoemDTO.setContent(Utils.removeBrTag(content));
        return editPoemDTO;
    }

    //  метод, возвращающий превью стихотворений для конкретного автора
    @GetMapping("/authors/{id}/poems")
    public ResponseEntity<List<? extends CompositionDTO>> getPoemsByAuthorId(
            @PathVariable long id,
            Principal principal){
        String principalName = "";
        principalName = (Objects.isNull(principal)) ? "default" : principal.getName();
        List<? extends CompositionDTO> lpd =  poemService.getPoemsByAuthorID(principalName, id);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(lpd.size()))
                .body(lpd);
    }
}
