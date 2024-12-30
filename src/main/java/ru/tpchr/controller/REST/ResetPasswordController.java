package ru.tpchr.controller.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tpchr.entities.security.Author;
import ru.tpchr.exceptions.AuthorExistsException;
import ru.tpchr.services.AuthorService;

@RestController
public class ResetPasswordController {
    private AuthorService authorService;

    @Autowired
    public void setService(AuthorService authorService) {
        this.authorService = authorService;
    }

    //  метод, высылающий письмо на электронную почту со ссылкой на напоминание пароля
//  в качестве аргумента получает адрес почты, на который нужно отправить ссылку
    @PostMapping("/remindPassword")
    public HttpStatus resetPassword(@RequestParam String email){
        Author author = authorService.getAuthorByEmail(email);
        if (author == null)
            throw new AuthorExistsException();
        authorService.createResetPassToken(author);
        return HttpStatus.OK;
    }

    @PostMapping("/password")
    public HttpStatus updatePassword(@RequestParam("id") long id,
                                     @RequestParam("password") String password){
        authorService.updateAuthorById(password, id);
        return HttpStatus.OK;
    }
}
