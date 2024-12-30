package ru.tpchr.controller.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tpchr.entities.Review;
import ru.tpchr.services.ReviewService;

@RestController
public class ReviewController {
    private ReviewService reviewService;

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/main/savereview")
    public HttpStatus saveReview(@ModelAttribute Review review){
        if(reviewService.save(review) != null)
            return HttpStatus.OK;
        else
            return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
