package com.ssafy.ssafit.message.service;

import com.ssafy.ssafit.message.dto.Message;
import java.util.List;

public interface MessageService {
    void sendMessage(Message message);
    List<Message> getReceivedMessages(int userId);
    List<Message> getSentMessages(int userId);
    void readMessage(long messageId);
}
