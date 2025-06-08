package com.ssafy.ssafit.video.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ssafit.util.JwtUtil;
import com.ssafy.ssafit.video.dto.Video;
import com.ssafy.ssafit.video.service.FileService;
import com.ssafy.ssafit.video.service.VideoService;

@RestController
@RequestMapping("/api/videos")
@CrossOrigin(origins = "*")
public class VideoController {

    @Autowired
    private VideoService videoService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private FileService fileService;

    // 영상 등록
    @PostMapping
    public ResponseEntity<?> addVideo(@RequestBody Video video, @RequestHeader("Authorization") String token) {
        try {
            String jwt = token.replace("Bearer ", "");
            int userId = jwtUtil.getUserId(jwt); // ✅ 정수 ID 추출

            video.setUserId(userId);
            videoService.addVideo(video);

            return ResponseEntity.ok("영상 등록 성공");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("영상 등록 실패: " + e.getMessage());
        }
    }
    
    @PostMapping("/personal")
    public ResponseEntity<?> uploadPersonalVideo(
        @RequestParam("title") String title,
        @RequestParam("description") String description,
        @RequestParam("video") MultipartFile video,
        @RequestParam("thumbnail") MultipartFile thumbnail,
        @RequestHeader("Authorization") String token
    ) {
        try {
            String jwt = token.replace("Bearer ", "");
            int userId = jwtUtil.getUserId(jwt);

            String videoUrl = fileService.saveFile(video, "videos");
            String thumbnailUrl = fileService.saveFile(thumbnail, "thumbnails");

            Video newVideo = new Video();
            newVideo.setUserId(userId);
            newVideo.setTitle(title);
            newVideo.setDescription(description);
            newVideo.setVideoUrl(videoUrl);
            newVideo.setThumbnail(thumbnailUrl);
            newVideo.setYoutubeId(null);
            newVideo.setType("PERSONAL");

            videoService.addVideo(newVideo);
            return ResponseEntity.ok("업로드 완료");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("업로드 실패: " + e.getMessage());
        }
    }
    // 전체 영상 조회
    @GetMapping
    public List<Video> getVideosByType(@RequestParam(value = "type", required = false) String type) {
        if (type == null || type.equalsIgnoreCase("ALL")) {
            return videoService.getAllVideos();
        } else if (type.equalsIgnoreCase("PERSONAL")) {
            return videoService.getVideosByType("PERSONAL");
        } else if (type.equalsIgnoreCase("YOUTUBE")) {
            return videoService.getYoutubeVideos(); // ✅ 추가로 만듦
        } else {
            return List.of(); // 잘못된 타입
        }
    }


    // 개별 영상 조회
    @GetMapping("/{id}")
    public Video getVideoById(@PathVariable Long id) {
        return videoService.getVideoById(id);
    }

    // 영상 수정
    @PutMapping("/{id}")
    public void updateVideo(@PathVariable Long id, @RequestBody Video video) {
        video.setVideoId(id); // path의 id를 DTO에 반영
        videoService.updateVideo(video);
    }

    // 영상 삭제
    @DeleteMapping("/{id}")
    public void deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
    }

    // 검색 기능
    @GetMapping("/search")
    public List<Video> search(@RequestParam String keyword) {
        return videoService.search(keyword);
    }
}