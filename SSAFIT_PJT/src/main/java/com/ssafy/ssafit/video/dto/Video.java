package com.ssafy.ssafit.video.dto;

import java.sql.Timestamp;

public class Video {
    private Long videoId;
    private String title;
    private String description;
    private String youtubeId;
    private String thumbnail;
    private String videoUrl;
    private String type;
    private Timestamp createdAt;
    private int userId;

    // 기본 생성자
    public Video() {}

    // 모든 필드 생성자 (필요하면 추가)
    public Video(Long videoId, String title, String description, String youtubeId,
                 String thumbnail, String videoUrl, Timestamp createdAt, int userId) {
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.youtubeId = youtubeId;
        this.thumbnail = thumbnail;
        this.videoUrl = videoUrl;
        this.createdAt = createdAt;
        this.userId = userId;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
    // Getter & Setter
    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // toString()
    @Override
    public String toString() {
        return "Video{" +
                "videoId=" + videoId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", youtubeId='" + youtubeId + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", createdAt=" + createdAt +
                ", userId=" + userId +
                '}';
    }
}
