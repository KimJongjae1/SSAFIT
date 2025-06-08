package com.ssafy.ssafit.review.dto;

import java.time.LocalDateTime;

public class Review {
    private Long reviewId;
    private String content;
    private LocalDateTime createdAt;
    private Long videoId;
    private Long userId;
    private String nickName;

    public Review() {}

    public Review(Long reviewId, String content, LocalDateTime createdAt, Long videoId, Long userId) {
        this.reviewId = reviewId;
        this.content = content;
        this.createdAt = createdAt;
        this.videoId = videoId;
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
	public Long getReviewId() {
		return reviewId;
	}
   
	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Long getVideoId() {
		return videoId;
	}

	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", content=" + content + ", createdAt=" + createdAt + ", videoId="
				+ videoId + ", userId=" + userId + "]";
	}

    // Getters & Setters
    
}