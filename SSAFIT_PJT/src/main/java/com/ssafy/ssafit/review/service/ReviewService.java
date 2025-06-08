package com.ssafy.ssafit.review.service;

import java.util.List;
import com.ssafy.ssafit.review.dto.Review;

public interface ReviewService {

    Review writeReview(Review review);

    List<Review> getReviewsByVideoId(Long videoId);

    void updateReview(Review review);

    void deleteReview(Long reviewId);
}