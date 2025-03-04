package com.challengerstory.chattingstory.chattingroom.application.service;

import com.challengerstory.chattingstory.chattingroom.aggregate.entity.ChattingRoom;
import com.challengerstory.chattingstory.chattingroom.repository.ChattingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChattingRoomService {

    private final ChattingRoomRepository chattingRoomRepository;

    public ChattingRoom createChattingRoom(String name) {
        ChattingRoom chattingRoom = new ChattingRoom();
        chattingRoom.setTitle(name);
        return chattingRoomRepository.save(chattingRoom);
    }
    public List<ChattingRoom> getChattingRooms(){
        return chattingRoomRepository.findAll();
    }

}
