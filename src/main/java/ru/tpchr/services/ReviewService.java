package ru.tpchr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tpchr.entities.Review;
import ru.tpchr.repository.ReviewRepo;
import ru.tpchr.repository.security.AuthorRepo;
import ru.tpchr.utils.Utils;

@Service
public class ReviewService {
    private ReviewRepo reviewRepo;
    private AuthorRepo authorRepo;

    @Autowired
    public void setReviewRepo(ReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    @Autowired
    public void setAuthorRepo(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    //  метод получения имени автора отзыва
    public String getNameIfAuthorRegistered(String email){
        return authorRepo.getAuthorDTOByEmail(email).getFirstName();
    }

    //  метод сохранения отзыва в базе
    public Review save(Review review){
        review.setDateTime(Utils.convertTimeToString());
        return reviewRepo.save(review);
    }
}
