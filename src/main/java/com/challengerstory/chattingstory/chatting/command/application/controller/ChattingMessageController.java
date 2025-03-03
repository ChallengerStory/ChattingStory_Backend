package com.challengerstory.chattingstory.chatting.command.application.controller;

import com.challengerstory.chattingstory.chatting.aggregate.entity.ChattingMessage;
import com.challengerstory.chattingstory.chatting.command.application.service.ChattingService;
import com.challengerstory.chattingstory.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChattingMessageController {

    private final ChattingService chattingService;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * WebSocket을 통한 메시지 전송 엔드포인트
     */
    @MessageMapping("/send-message")
    public void sendMessage(@Payload ChattingMessage chattingMessage) {
        ChattingMessage savedMessage = chattingService.sendMessage(chattingMessage);
        // 채팅방 참가자들에게 메시지 브로드캐스트
        messagingTemplate.convertAndSend("/topic/room/" + chattingMessage.getRoomId(), savedMessage);
    }

    /**
     * 특정 채팅방의 메시지 조회
     */
    @GetMapping("/room/{roomId}/messages")
    public ResponseDTO<?> getRoomMessages(
            @PathVariable String roomId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseDTO.ok(chattingService.getChatMessages(roomId, page, size));
    }


}