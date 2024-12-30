package ru.tpchr.controller.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.tpchr.entities.Composition;
import ru.tpchr.entities.security.Author;
import ru.tpchr.services.AuthorService;
import ru.tpchr.services.ComposeService;

import java.security.Principal;

@RestController
public class LikesController {
    private AuthorService authorService;
    private ComposeService composeService;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setComposeService(ComposeService composeService) {
        this.composeService = composeService;
    }

    //  добавление или удаление лайка
    @GetMapping("/poem/likes/{id}")
    public String manageLikes(@PathVariable long id,
                              Principal principal) {
        Author author = authorService.getAuthorByEmail(principal.getName());
        Composition comp = composeService.getListOfLikes(id);
        boolean status = false;
        if (!comp.getLikes().contains(author)) {
            comp.getLikes().add(author);
            status = true;
        } else {
            comp.getLikes().remove(author);
        }
        composeService.save(comp);
        return String.format("{\"status\": %s, \"count\": %d}", status, comp.getLikes().size());
    }
}
