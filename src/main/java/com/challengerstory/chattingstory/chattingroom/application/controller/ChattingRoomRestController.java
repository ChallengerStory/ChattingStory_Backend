package com.challengerstory.chattingstory.chattingroom.application.controller;

import com.challengerstory.chattingstory.chattingroom.aggregate.entity.ChattingRoom;
import com.challengerstory.chattingstory.chattingroom.application.service.ChattingRoomService;
import com.challengerstory.chattingstory.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// HTTP REST API를 위한 채팅방 컨트롤러
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat/room")
public class ChattingRoomRestController {

    private final ChattingRoomService chattingRoomService;

    @PostMapping
    public ResponseDTO<?> createRoom(@RequestBody ChattingRoom chattingRoom) {
        return ResponseDTO.ok(chattingRoomService.createChattingRoom(chattingRoom.getTitle()));
    }

}