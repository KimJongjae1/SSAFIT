package com.ssafy.ssafit.message.controller;

import com.ssafy.ssafit.message.dto.Message;
import com.ssafy.ssafit.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public void sendMessage(@RequestBody Message message) {
        messageService.sendMessage(message);
    }

    @GetMapping("/received/{userId}")
    public List<Message> getReceived(@PathVariable int userId) {
        return messageService.getReceivedMessages(userId);
    }

    @GetMapping("/sent/{userId}")
    public List<Message> getSent(@PathVariable int userId) {
        return messageService.getSentMessages(userId);
    }

    @PutMapping("/{messageId}/read")
    public void read(@PathVariable long messageId) {
        messageService.readMessage(messageId);
    }
}
