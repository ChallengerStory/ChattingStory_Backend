package com.challengerstory.chattingstory.chatting.command.application.service;

import com.challengerstory.chattingstory.chatting.aggregate.entity.ChattingRoom;
import com.challengerstory.chattingstory.chatting.command.respository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChattingRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChattingRoom createChatRoom(String name) {
        ChattingRoom chattingRoom = new ChattingRoom();
        chattingRoom.setTitle(name);
        return chatRoomRepository.save(chattingRoom);
    }
    public List<ChattingRoom> getChattingRooms(){
        return chatRoomRepository.findAll();
    }


}
