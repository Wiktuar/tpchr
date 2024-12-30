package ru.tpchr.controller.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tpchr.entities.security.Author;
import ru.tpchr.exceptions.AuthorExistsException;
import ru.tpchr.services.AuthorService;

@RestController
public class RepeatActivateController {
    private AuthorService authorService;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/activate/repeat")
    public ResponseEntity<String> getRepeatActivationEmail(@RequestParam String email){
        Author author = authorService.getAuthorByEmail(email);
        if (author == null)
            throw new AuthorExistsException();
        authorService.getRepeatActivationEmail(author);
        return ResponseEntity.ok().build();
    }
}
