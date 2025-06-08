package com.ssafy.ssafit.message.service;

import com.ssafy.ssafit.message.dao.MessageDao;
import com.ssafy.ssafit.message.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public void sendMessage(Message message) {
        messageDao.insertMessage(message);
    }

    @Override
    public List<Message> getReceivedMessages(int userId) {
        return messageDao.selectReceivedMessages(userId);
    }

    @Override
    public List<Message> getSentMessages(int userId) {
        return messageDao.selectSentMessages(userId);
    }

    @Override
    public void readMessage(long messageId) {
        messageDao.markAsRead(messageId);
    }
}
