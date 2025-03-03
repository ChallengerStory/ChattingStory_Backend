package com.challengerstory.chattingstory.chatting.command.application.service;

import com.challengerstory.chattingstory.chatting.aggregate.entity.ChatRoom;
import com.challengerstory.chattingstory.chatting.command.respository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChattingRoomService {

    private ChatRoomRepository chatRoomRepository;

    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setTitle(name);
        return chatRoomRepository.save(chatRoom);
    }


}
