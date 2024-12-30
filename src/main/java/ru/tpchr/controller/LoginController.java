package ru.tpchr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.tpchr.DTO.AuthorCabinetDTO;
import ru.tpchr.entities.security.Author;
import ru.tpchr.entities.security.PasswordResetToken;
import ru.tpchr.exceptions.TokenExistsException;
import ru.tpchr.services.AuthorService;
import ru.tpchr.utils.ConvertEntityToDTO;
import ru.tpchr.utils.HeaderMenuUtil;
import ru.tpchr.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class LoginController {

    private AuthorService authorService;
    private HeaderMenuUtil headerMenuUtil;

    @Autowired
    public void setHeaderMenuUtil(HeaderMenuUtil headerMenuUtil) {
        this.headerMenuUtil = headerMenuUtil;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    // получение страницы регистрации автора
    @GetMapping("/registration")
    public String getRegistrationPage(){
        return "personal/registration";
    }

    //метод редактирования персонаьных данных пользователя
    @GetMapping("/cabinet/editauthor")
    public String getEditAuthorPage(Principal principal, Model model){
        Author authorFromDb = authorService.getAuthorByEmail(principal.getName());

        AuthorCabinetDTO author =
                ConvertEntityToDTO.convertToAuthorCabinetDto(authorFromDb);

        author.setDescription(Utils.removeBrTagDescription(author.getDescription()));
        model.addAttribute("author", author);
        model.addAttribute("authorDTO", headerMenuUtil.getAuthorDTO());
        return "personal/editAuthor";
    }

    // активация аккаунта автора
    @GetMapping("/activate/{code}")
    public String activateUser(@PathVariable("code") String code,
                               Model model){
        boolean isActivated = authorService.activateAuthor(code);
        if(isActivated)model.addAttribute("activate", "Поздравляем! Ваш аккаунт успешно активирован. Электронная почта подтверждена.");
        else model.addAttribute("activate", "Активировать аккаунт не получилось. Возможно, он уже активирован." +
                "Попробуйте перейти в личный кабинет или обратитесь в <a href=\"mailto:tech@tphr.ru\" class=\"support\">Техническую поддержку.</a>");
        return "personal/activation";
    }

    //  метод получения страницы сброса пароля
    @GetMapping("/reset/{token}")
    public String resetPassword(@PathVariable("token") String token,
                                Model model){

        PasswordResetToken prt = null;
        try{
            prt = authorService.getPasswordResetToken(token);
        } catch(TokenExistsException e){
            model.addAttribute("author_nor_found", e.getMessage());
            model.addAttribute("author_id", " ");
            return "personal/resetPassword";
        }
        model.addAttribute("author_id", prt.getAuthor().getId());
        return "personal/resetPassword";
    }

//    метод, помогающий обработать случай, когда пользователь аутентифицировался, перешел куда, и ему нужно нажать "Назад"
//    и при этом не попасть снова на форму логирования
    @GetMapping("/login")
    public String showLoginForm(Model model,
                                HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "personal/login";
        }
        return "redirect:/";
    }

}
