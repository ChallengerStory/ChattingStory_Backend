package com.challengerstory.chattingstory.chatting.command.application.controller;

import com.challengerstory.chattingstory.chatting.aggregate.entity.ChattingRoom;
import com.challengerstory.chattingstory.chatting.command.application.service.ChattingRoomService;
import com.challengerstory.chattingstory.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat/rooms")
public class ChattingRoomController {

    private final ChattingRoomService chattingRoomService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public ResponseDTO<?> createChattingRoom(@RequestBody ChattingRoom chattingRoom){
        return ResponseDTO.ok(chattingRoomService.createChatRoom(chattingRoom.getTitle()));
    }


}
