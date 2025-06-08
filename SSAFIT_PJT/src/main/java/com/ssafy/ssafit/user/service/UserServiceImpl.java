package com.ssafy.ssafit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ssafit.user.dao.UserDao;
import com.ssafy.ssafit.user.dto.User;
import com.ssafy.ssafit.user.dto.UserSignUpRequestDto;
import com.ssafy.ssafit.user.dto.UserUpdateRequestDto;
import com.ssafy.ssafit.util.JwtUtil;

@Service
public class UserServiceImpl implements UserService {

	// UserServiceImpl.java
	@Autowired
	private UserDao userDao;

	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public String login(String id, String password) {
	    User user = userDao.findById(id);
	    if (user != null && user.getPassword().equals(password)) {
	        return jwtUtil.generateToken(user.getId(), user.getNickName(), user.getUserId()); // ✅ 여기도 숫자 ID 추가
	    }
	    return null;
	}
	
	@Override
	public User getUserByUserId(Long userId) {
	    return userDao.findByUserId(userId);
	}
	
	@Override
	public User getUserInfo(String id) {
	    return userDao.findById(id);
	}
	
	@Override
	public boolean updateUser(int userId, UserUpdateRequestDto dto) {
	    return userDao.updateUser(userId, dto) > 0;
	}
	
	@Override
	public boolean signup(UserSignUpRequestDto dto) {
	    return userDao.insertUser(dto) > 0;
	}
	
	@Override
	public User findById(String id) {
	    return userDao.findById(id);
	}


}