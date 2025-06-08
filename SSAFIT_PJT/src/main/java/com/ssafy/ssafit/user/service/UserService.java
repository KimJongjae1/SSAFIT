package com.ssafy.ssafit.user.service;

import com.ssafy.ssafit.user.dto.User;
import com.ssafy.ssafit.user.dto.UserSignUpRequestDto;
import com.ssafy.ssafit.user.dto.UserUpdateRequestDto;

public interface UserService {
	
	String login(String id, String password); // 반환값: JWT 토큰
	
	User getUserInfo(String id);
	
	boolean updateUser(int userId, UserUpdateRequestDto dto);

	boolean signup(UserSignUpRequestDto dto);
	
	User getUserByUserId(Long userId);
	
	User findById(String id);  // ✅ 아이디로 사용자 정보 조회

}