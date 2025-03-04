package com.challengerstory.chattingstory.chatting.application.controller;

import com.challengerstory.chattingstory.chatting.application.service.ChattingService;
import com.challengerstory.chattingstory.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChattingRestController {

    private final ChattingService chattingService;

    @GetMapping("/room/{roomId}/messages")
    public ResponseDTO<?> getRoomMessages(
            @PathVariable String roomId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseDTO.ok(chattingService.getChatMessages(roomId, page, size));
    }

}