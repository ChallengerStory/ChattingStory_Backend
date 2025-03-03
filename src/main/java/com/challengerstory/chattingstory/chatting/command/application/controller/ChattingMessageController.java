package com.challengerstory.chattingstory.chatting.command.application.controller;

import com.challengerstory.chattingstory.chatting.aggregate.entity.ChatMessage;
import com.challengerstory.chattingstory.chatting.command.application.service.ChattingService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chatting")
public class ChattingMessageController {

    private final ChattingService chattingService;

    /**
     * 메시지 전송 엔드포인트
     */
    @MessageMapping("/send-message")
    public void sendMessage(ChatMessage chatMessage) {
        chattingService.sendMessage(chatMessage);
    }



}
