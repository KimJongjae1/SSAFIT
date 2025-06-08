package com.ssafy.ssafit.review.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ssafit.review.dao.ReviewDao;
import com.ssafy.ssafit.review.dto.Review;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Override
    public Review writeReview(Review review) {
        reviewDao.insertReview(review);
        return review;
    }

    @Override
    public List<Review> getReviewsByVideoId(Long videoId) {
        return reviewDao.selectByVideoId(videoId);
    }

    @Override
    public void updateReview(Review review) {
        reviewDao.updateReview(review);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewDao.deleteReview(reviewId);
    }
}