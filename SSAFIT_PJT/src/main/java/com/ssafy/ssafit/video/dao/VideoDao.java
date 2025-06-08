package com.ssafy.ssafit.video.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.ssafit.video.dto.Video;


@Mapper
public interface VideoDao {

    // 영상 등록
    void insertVideo(Video video);

    List<Video> selectByType(String type);
    List<Video> selectYoutube(); // ✅ 추가
    
    // 영상 전체 조회
    List<Video> selectAll();

    // 영상 id로 조회
    Video selectById(Long id);

    // 영상 수정
    void updateVideo(Video video);

    // 영상 삭제
    void deleteVideo(Long id);

    // 영상 검색 (부위별, 제목 등 키워드)
    List<Video> search(String keyword);
}