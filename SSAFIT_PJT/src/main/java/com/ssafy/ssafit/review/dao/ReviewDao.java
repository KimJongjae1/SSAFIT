package com.ssafy.ssafit.review.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.ssafit.review.dto.Review;

@Mapper
public interface ReviewDao {

    void insertReview(Review review);

    List<Review> selectByVideoId(Long videoId);

    void updateReview(Review review);

    void deleteReview(Long reviewId);
}
