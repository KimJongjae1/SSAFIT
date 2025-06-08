package com.ssafy.ssafit.review.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.review.dto.Review;
import com.ssafy.ssafit.review.service.ReviewService;
import com.ssafy.ssafit.user.dto.User;
import com.ssafy.ssafit.user.service.UserService;
import com.ssafy.ssafit.util.JwtUtil;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
  
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    // 리뷰 작성
    @PostMapping
    public ResponseEntity<?> writeReview(@RequestBody Review review,
                                         @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");

            // ✅ 수정: DB PK는 이제 이 메서드로 뽑아야 함
            int userId = jwtUtil.getUserId(token);

            User user = userService.getUserByUserId((long) userId);

            // ✅ 리뷰 작성자 정보 설정
            review.setUserId((long) userId); // Long으로 변환 필요 시
            review.setNickName(user.getNickName());

            Review savedReview = reviewService.writeReview(review); // 저장된 리뷰 반환

            return ResponseEntity.status(201).body(savedReview);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("리뷰 작성 중 오류 발생: " + e.getMessage());
        }
    }



    // 영상별 리뷰 조회
    @GetMapping("/video/{videoId}")
    public ResponseEntity<List<Review>> getReviewsByVideo(@PathVariable Long videoId) {
        try {
            List<Review> reviews = reviewService.getReviewsByVideoId(videoId);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // 간단하게 처리
        }
    }

    // 리뷰 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable Long reviewId, @RequestBody Review review) {
        try {
            review.setReviewId(reviewId);
            reviewService.updateReview(review);
            return ResponseEntity.ok("리뷰 수정 완료");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("리뷰 수정 실패: " + e.getMessage());
        }
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        try {
            reviewService.deleteReview(reviewId);
            return ResponseEntity.ok("리뷰 삭제 완료");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("리뷰 삭제 실패: " + e.getMessage());
        }
    }
}
