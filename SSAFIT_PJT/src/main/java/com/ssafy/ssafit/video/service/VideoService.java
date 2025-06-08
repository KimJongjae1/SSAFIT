package com.ssafy.ssafit.video.service;

import java.util.List;
import com.ssafy.ssafit.video.dto.Video;

public interface VideoService {

    void addVideo(Video video);

    List<Video> getAllVideos();

    Video getVideoById(Long id);

    void updateVideo(Video video);

    void deleteVideo(Long id);
    
    List<Video> getVideosByType(String type);
   
    List<Video> getYoutubeVideos() ;
    
    List<Video> search(String keyword);
}
