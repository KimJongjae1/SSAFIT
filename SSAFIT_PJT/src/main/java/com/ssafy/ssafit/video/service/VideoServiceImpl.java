package com.ssafy.ssafit.video.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ssafit.video.dao.VideoDao;
import com.ssafy.ssafit.video.dto.Video;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;

    @Override
    public void addVideo(Video video) {
        videoDao.insertVideo(video);
    }

    @Override
    public List<Video> getAllVideos() {
        return videoDao.selectAll();
    }

    @Override
    public Video getVideoById(Long id) {
        return videoDao.selectById(id);
    }
    
    @Override
    public List<Video> getVideosByType(String type) {
        return videoDao.selectByType(type);
    }

    @Override
    public List<Video> getYoutubeVideos() {
        return videoDao.selectYoutube(); // ✅ 새로 만들 메서드
    }

    

    @Override
    public void updateVideo(Video video) {
        videoDao.updateVideo(video);
    }

    @Override
    public void deleteVideo(Long id) {
        videoDao.deleteVideo(id);
    }

    @Override
    public List<Video> search(String keyword) {
        return videoDao.search(keyword);
    }
}