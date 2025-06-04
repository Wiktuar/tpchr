package ru.tpchr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.tpchr.DTO.AuthorCabinetDTO;
import ru.tpchr.DTO.CompositionDTO;
import ru.tpchr.DTO.LikesPoemDto;
import ru.tpchr.entities.security.Author;
import ru.tpchr.services.AuthorService;
import ru.tpchr.services.ContentService;
import ru.tpchr.services.PoemService;
import ru.tpchr.utils.ConvertEntityToDTO;
import ru.tpchr.utils.HeaderMenuUtil;
import ru.tpchr.utils.Utils;

import java.security.Principal;
import java.util.List;

@Controller
public class CabinetController {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${poem.cover}")
    private String poemCover;

    private PoemService poemService;
    private AuthorService authorService;
    private ContentService contentService;
    private HeaderMenuUtil headerMenuUtil;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setPoemService(PoemService poemService) {
        this.poemService = poemService;
    }

    @Autowired
    public void setContentService(ContentService contentService) {
        this.contentService = contentService;
    }

    @Autowired
    public void setHeaderMenuUtil(HeaderMenuUtil headerMenuUtil) {
        this.headerMenuUtil = headerMenuUtil;
    }

    //  метод получения страницы личного кабинета
    @GetMapping("/cabinet")
    public String getCabinet(Principal principal, Model model){
        Author authorFromDb = authorService.getAuthorByEmail(principal.getName());

        AuthorCabinetDTO author =
                ConvertEntityToDTO.convertToAuthorCabinetDto(authorFromDb);

        model.addAttribute("author", author);
        model.addAttribute("authorDTO", headerMenuUtil.getAuthorDTO());
        return "personal/cabinet";
    }

    //  получение всех стихотворений одного автора
    @GetMapping("/cabinet/poems")
    public String getAllLikesPoemDto(Model model,
                                     Principal principal){
        List<? extends CompositionDTO> lpd =  poemService.getPoemsByUser(principal.getName(),principal.getName());
        model.addAttribute("poems", lpd);
        model.addAttribute("authorDTO", headerMenuUtil.getAuthorDTO());
        return "cabinet/poems";
    }

    //  метод, возвращающий стихотворение с его лайками и комментариями
    @GetMapping("/cabinet/poem/{id}")
    public String getPoemById(@PathVariable long id,
                              Principal principal,
                              Model model){
        LikesPoemDto likesPoemDto = poemService.getPoemDtoWithLikesAndComments(principal.getName(), id);
        likesPoemDto.setReleaseDate(Utils.getFormatedDate(likesPoemDto.getReleaseDate()));
        String content = contentService.findById(id).getContent();
        likesPoemDto.setContent(content);
        model.addAttribute("poem", likesPoemDto);
        model.addAttribute("authorDTO", headerMenuUtil.getAuthorDTO());
        return "cabinet/poem";
    }

    //  метод, удаляющий стихотворение их базы данных
    @GetMapping("/cabinet/delete/poem/{id}")
    public String deletePoemById(@PathVariable long id){
        contentService.deletePoemById(id);
        return "redirect:/cabinet/poems";
    }

    //  метод возвращает страницу заглушку для неразработанных разделов личного кабинета
    @GetMapping("/cabinet/mock")
    public String getMockPage(Model model){
        model.addAttribute("authorDTO", headerMenuUtil.getAuthorDTO());
        return "cabinet/mockPage";
    }
}
