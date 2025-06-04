package ru.tpchr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.tpchr.DTO.CompositionDTO;
import ru.tpchr.DTO.LikesAlbumDto;
import ru.tpchr.services.music.AlbumService;
import ru.tpchr.utils.HeaderMenuUtil;
import ru.tpchr.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class MusicPageController {
    private AlbumService albumService;
    private HeaderMenuUtil headerMenuUtil;

    @Autowired
    public void setAlbumService(AlbumService albumService) {
        this.albumService = albumService;
    }

    @Autowired
    public void setHeaderMenuUtil(HeaderMenuUtil headerMenuUtil) {
        this.headerMenuUtil = headerMenuUtil;
    }

    //  метод получения всех альбомов автора
    @GetMapping("/cabinet/music")
    public String getAllAlbums(Principal principal,
                               Model model){
        List<? extends CompositionDTO> albums = albumService.getAlbumsByUser(principal.getName());
        model.addAttribute("albums", albums);
        model.addAttribute("authorDTO", headerMenuUtil.getAuthorDTO());
        return "cabinet/musics";
    }

    //  метод получения отдельного альбома по его ID
    @GetMapping(value = {"/cabinet/music/{id}", "/main/music/{id}"})
    public String getAlbumByID(@PathVariable("id") long id,
                               HttpServletRequest request,
                               Model model) {
        LikesAlbumDto lDto = albumService.getLikesAlbumDto(headerMenuUtil.getPrincipalName(), id);
        lDto.setReleaseDate(Utils.getFormatedDate(lDto.getReleaseDate()));
        model.addAttribute("album", lDto);
        model.addAttribute("authorDTO", headerMenuUtil.getAuthorDTO());
        return (request.getRequestURL().toString().contains("/main/music")) ? "single/singlePlayer" : "cabinet/music";
    }

    //  метод удаления стихотворения по его ID
    @GetMapping("/cabinet/delete/album/{id}")
    public String deleteAlbumById(@PathVariable long id){
        albumService.deleteAlbumById(id);
        return"redirect:/cabinet/music";
    }
}
