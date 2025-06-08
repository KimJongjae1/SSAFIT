// UserController.java
package com.ssafy.ssafit.user.controller;

import com.ssafy.ssafit.user.dto.*;
import com.ssafy.ssafit.user.service.UserService;
import com.ssafy.ssafit.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtil jwtUtil;

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
	    String token = userService.login(loginRequest.getId(), loginRequest.getPassword());

	    if (token != null) {
	        // ✅ 로그인 성공 시 사용자 정보 조회
	        User user = userService.findById(loginRequest.getId());

	        // ✅ 닉네임 + userId 포함 응답 반환
	        Map<String, Object> response = new HashMap<>();
	        response.put("token", token);
	        response.put("nickName", user.getNickName());
	        response.put("userId", user.getUserId()); // ✅ 요게 핵심!

	        return ResponseEntity.ok(response);
	    } else {
	        return ResponseEntity.status(401).body("Invalid ID or password");
	    }
	}



	// 회원정보 조회
	@GetMapping("/mypage")
	public ResponseEntity<?> getMyInfo(@RequestHeader("Authorization") String tokenHeader) {
	    String token = tokenHeader.replace("Bearer ", "");
	    System.out.println("🔐 받은 토큰: " + token);

	    try {
	        if (!jwtUtil.validateToken(token)) {
	            System.out.println("❌ 토큰 유효성 검사 실패");
	            return ResponseEntity.status(401).body("Invalid token");
	        }

	        Long userId = Long.parseLong(jwtUtil.getClaims(token).getSubject());
	        System.out.println("✅ 파싱된 userId: " + userId);

	        User user = userService.getUserByUserId(userId);
	        return ResponseEntity.ok(user);

	    } catch (ExpiredJwtException e) {
	        System.out.println("❌ 만료된 토큰");
	        return ResponseEntity.status(401).body("Token expired. Please login again.");
	    }
	}


	
	// 회원정보 수정
	@PutMapping("/update")
	public ResponseEntity<?> updateUser(
	        @RequestHeader("Authorization") String tokenHeader,
	        @RequestBody UserUpdateRequestDto updateRequest) {

	    String token = tokenHeader.replace("Bearer ", "");

	    try {
	        if (!jwtUtil.validateToken(token)) {
	            return ResponseEntity.status(401).body("Invalid token");
	        }
	    } catch (ExpiredJwtException e) {
	        return ResponseEntity.status(401).body("Token expired. Please login again.");
	    }

	    int userId = jwtUtil.getUserId(token);
	    boolean success = userService.updateUser(userId, updateRequest);

	    if (success) {
	        return ResponseEntity.ok("User updated successfully");
	    } else {
	        return ResponseEntity.status(500).body("Update failed");
	    }
	}

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody UserSignUpRequestDto signUpRequest) {
	    boolean success = userService.signup(signUpRequest);

	    if (success) {
	        return ResponseEntity.ok("회원가입 성공");
	    } else {
	        return ResponseEntity.status(500).body("회원가입 실패");
	    }
	}

	
}//class
