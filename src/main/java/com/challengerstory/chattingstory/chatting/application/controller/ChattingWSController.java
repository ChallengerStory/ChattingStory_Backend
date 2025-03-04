package com.challengerstory.chattingstory.chatting.application.controller;

import com.challengerstory.chattingstory.chatting.aggregate.entity.ChattingMessage;
import com.challengerstory.chattingstory.chatting.application.service.ChattingService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

// WebSocket 통신을 위한 컨트롤러
@RequiredArgsConstructor
@Controller
public class ChattingWSController {

    private final ChattingService chattingService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/send-message")
    public void sendMessage(@Payload ChattingMessage chattingMessage) {
        ChattingMessage savedMessage = chattingService.sendMessage(chattingMessage);
        messagingTemplate.convertAndSend("/topic/room/" + chattingMessage.getRoomId(), savedMessage);
    }

}