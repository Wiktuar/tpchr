package ru.tpchr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.tpchr.services.ReviewService;
import ru.tpchr.utils.HeaderMenuUtil;

import java.security.Principal;

@Controller
public class ReviewPageController {
    private ReviewService reviewService;
    private HeaderMenuUtil headerMenuUtil;

    @Autowired
    public void setHeaderMenuUtil(HeaderMenuUtil headerMenuUtil) {
        this.headerMenuUtil = headerMenuUtil;
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/main/review")
    public String getReviewPage(Model model, Principal principal){
        if(principal != null){
            model.addAttribute("email", principal.getName());
            model.addAttribute("name", reviewService.getNameIfAuthorRegistered(principal.getName()));
        }
        model.addAttribute("authorDTO", headerMenuUtil.getAuthorDTO());
        return "single/review";
    }
}
