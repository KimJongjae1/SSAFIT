package com.ssafy.ssafit.message.dao;

import com.ssafy.ssafit.message.dto.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageDao {
    void insertMessage(Message message);
    List<Message> selectReceivedMessages(@Param("userId") int userId);
    List<Message> selectSentMessages(@Param("userId") int userId);
    void markAsRead(@Param("messageId") long messageId);
}
