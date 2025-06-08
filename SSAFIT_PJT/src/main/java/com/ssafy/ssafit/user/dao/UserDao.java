package com.ssafy.ssafit.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.ssafit.user.dto.User;
import com.ssafy.ssafit.user.dto.UserSignUpRequestDto;
import com.ssafy.ssafit.user.dto.UserUpdateRequestDto;
@Mapper
public interface UserDao {
	
	User findByUserId(Long userId);
	
    User findById(String id);  // id로 회원 정보 조회
    
    int updateUser(@Param("userId") int userId, @Param("user") UserUpdateRequestDto user);


    
    int insertUser(UserSignUpRequestDto dto);

}