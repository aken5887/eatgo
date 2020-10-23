package kr.ardent.eatgo.application;

import kr.ardent.eatgo.domain.Review;
import kr.ardent.eatgo.domain.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    public Review addReview(Review review){
            //TODO : review Service
        return  reviewRepository.save(review);
    }
}
