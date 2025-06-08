package com.ssafy.ssafit.friend.controller;

import com.ssafy.ssafit.friend.service.FriendService;
import com.ssafy.ssafit.user.dto.User;
import com.ssafy.ssafit.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/friend")
@CrossOrigin(origins = "http://localhost:5173")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/follow")
    public ResponseEntity<?> follow(@RequestHeader("Authorization") String tokenHeader,
                                    @RequestBody Map<String, Integer> body) {
        String token = tokenHeader.replace("Bearer ", "");
        try {
            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(401).body("Invalid token");
            }

            int followerId = jwtUtil.getUserId(token);  // ✅ 변경됨
            int followeeId = body.get("followeeId");
            boolean result = friendService.follow(followerId, followeeId);
            return result ? ResponseEntity.ok("Followed") : ResponseEntity.badRequest().body("Already following");

        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(401).body("Token expired");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error");
        }
    }

    @PostMapping("/unfollow")
    public ResponseEntity<?> unfollow(@RequestHeader("Authorization") String tokenHeader,
                                      @RequestBody Map<String, Integer> body) {
        String token = tokenHeader.replace("Bearer ", "");
        try {
            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(401).body("Invalid token");
            }

            int followerId = jwtUtil.getUserId(token);  // ✅ 변경됨
            int followeeId = body.get("followeeId");
            boolean result = friendService.unfollow(followerId, followeeId);
            return result ? ResponseEntity.ok("Unfollowed") : ResponseEntity.badRequest().body("Not following");

        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(401).body("Token expired");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error");
        }
    }

    @GetMapping("/mutual")
    public ResponseEntity<?> mutualFriends(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");
        try {
            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(401).body("Invalid token");
            }

            int userId = jwtUtil.getUserId(token);  // ✅ 변경됨
            List<User> friends = friendService.getMutualFriends(userId);
            return ResponseEntity.ok(friends);

        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(401).body("Token expired");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error");
        }
    }
}
