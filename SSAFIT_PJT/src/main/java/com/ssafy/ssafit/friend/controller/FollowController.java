package com.ssafy.ssafit.friend.controller;

import com.ssafy.ssafit.friend.service.FollowService;
import com.ssafy.ssafit.user.dto.User;
import com.ssafy.ssafit.user.service.UserService;
import com.ssafy.ssafit.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
@CrossOrigin(origins = "http://localhost:5173")
public class FollowController {

    @Autowired
    private FollowService followService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ 기존 방식: 숫자 user_id 기반 팔로우
    @PostMapping("/{followeeId}")
    public ResponseEntity<?> followUser(@PathVariable int followeeId,
                                        @RequestHeader("Authorization") String tokenHeader) {
        try {
            String token = tokenHeader.replace("Bearer ", "");
            int followerId = jwtUtil.getUserId(token);

            boolean success = followService.follow(followerId, followeeId);

            if (success) {
                return ResponseEntity.ok("팔로우 성공 및 채팅방 생성 완료");
            } else {
                return ResponseEntity.status(400).body("이미 팔로우된 사용자거나 실패");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("서버 에러: " + e.getMessage());
        }
    }

    // ✅ 새 방식: 로그인 ID(id)로 팔로우
    @PostMapping("/by-id/{followeeLoginId}")
    public ResponseEntity<?> followByLoginId(@PathVariable String followeeLoginId,
                                             @RequestHeader("Authorization") String tokenHeader) {
        try {
            String token = tokenHeader.replace("Bearer ", "");
            int followerId = jwtUtil.getUserId(token); // 내 numeric user_id

            // followee의 login ID로 사용자 조회
            User followee = userService.findById(followeeLoginId);
            if (followee == null) {
                return ResponseEntity.status(404).body("해당 ID의 사용자가 존재하지 않습니다.");
            }

            int followeeId = followee.getUserId(); // followee의 numeric user_id
            boolean success = followService.follow(followerId, followeeId);

            if (success) {
                return ResponseEntity.ok("팔로우 성공 및 채팅방 생성 완료");
            } else {
                return ResponseEntity.status(400).body("이미 팔로우된 사용자거나 실패");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("서버 에러: " + e.getMessage());
        }
    }
}
