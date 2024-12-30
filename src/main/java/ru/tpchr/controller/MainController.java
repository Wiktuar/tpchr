package ru.tpchr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.tpchr.DTO.AllComposeDTO;
import ru.tpchr.DTO.LikesPoemDto;
import ru.tpchr.services.AllComposeService;
import ru.tpchr.services.AuthorService;
import ru.tpchr.services.ContentService;
import ru.tpchr.services.PoemService;
import ru.tpchr.utils.HeaderMenuUtil;

import java.util.List;

@Controller
public class MainController {
    private PoemService poemService;
    private AuthorService authorService;
    private ContentService contentService;
    private AllComposeService allComposeService;
    private HeaderMenuUtil headerMenuUtil;

    @Autowired
    public void setPoemService(PoemService poemService) {
        this.poemService = poemService;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setContentService(ContentService contentService) {
        this.contentService = contentService;
    }

    @Autowired
    public void setAllComposeService(AllComposeService allComposeService) {
        this.allComposeService = allComposeService;
    }

    @Autowired
    public void setHeaderMenuUtil(HeaderMenuUtil headerMenuUtil) {
        this.headerMenuUtil = headerMenuUtil;
    }

    //  метод получения стихотворений на индексной странице
    @GetMapping("/")
    public String getMainPage(Model model){
        List<AllComposeDTO> acd = allComposeService.getAllCompose(headerMenuUtil.getPrincipalName());
        model.addAttribute("authorDTO", headerMenuUtil.getAuthorDTO());
        model.addAttribute("allComposDTO", acd);
        return "index";
    }


    //  метод, возвращающий стихотворение с его лайками и комментариями
    @GetMapping("/main/poem/{id}")
    public String getPoemById(@PathVariable long id,
                              Model model){
        LikesPoemDto likesPoemDto = poemService.getPoemDtoWithLikesAndComments(headerMenuUtil.getPrincipalName(), id);
        String content = contentService.findById(id).getContent();
        likesPoemDto.setContent(content);
        model.addAttribute("authorDTO", headerMenuUtil.getAuthorDTO());
        model.addAttribute("poem", likesPoemDto);
        return "single/singlePoem";
    }

    @GetMapping("/target/poem/{id}")
    public String getLoginPoem(@PathVariable String id){
        String targetString = "/main/poem/" + id;
        return "redirect:" + targetString;
    }
}
