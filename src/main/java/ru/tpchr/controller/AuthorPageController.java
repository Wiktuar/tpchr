package ru.tpchr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.tpchr.DTO.AuthorCabinetDTO;
import ru.tpchr.services.AuthorService;
import ru.tpchr.utils.HeaderMenuUtil;

@Controller
public class AuthorPageController {
    private AuthorService authorService;
    private HeaderMenuUtil headerMenuUtil;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setHeaderMenuUtil(HeaderMenuUtil headerMenuUtil) {
        this.headerMenuUtil = headerMenuUtil;
    }

    @GetMapping("/main/author/{id}")
    public String getAuthorPage(@PathVariable long id,
                                Model model){
        AuthorCabinetDTO acd = authorService.getAuthorCabinetDTO(id);
        model.addAttribute("authorDTO", headerMenuUtil.getAuthorDTO());
        model.addAttribute("author", acd);
        return "single/author";
    }

    //   метод получения всех авторов
    @GetMapping("/main/authors")
    public String getAllAuthors(Model model){
        model.addAttribute("authorDTO", headerMenuUtil.getAuthorDTO());
        model.addAttribute("authors", authorService.getAllAuthors());
        return "authors";
    }
}
