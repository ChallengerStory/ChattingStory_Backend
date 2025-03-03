package com.challengerstory.chattingstory.chatting.command.application.service;


import com.challengerstory.chattingstory.chatting.aggregate.entity.ChatMessage;
import com.challengerstory.chattingstory.chatting.aggregate.entity.ChatRoom;
import com.challengerstory.chattingstory.chatting.aggregate.entity.MessageType;
import com.challengerstory.chattingstory.chatting.command.respository.ChatMessageRepository;
import com.challengerstory.chattingstory.chatting.command.respository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChattingService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;


    /**
     * 채팅 메시지 전송
     */
    public void sendMessage(ChatMessage chatMessage) {
        // 메시지 타입이 지정되지 않은 경우 CHAT으로 설정
        if (chatMessage.getType() == null) {
            chatMessage.setType(MessageType.CHAT);
        }

        // 타임스탬프 설정
        chatMessage.setCreatedAt(LocalDateTime.now());

        // 메시지 저장
        chatMessage = chatMessageRepository.save(chatMessage);

        // WebSocket을 통해 메시지 전송
        messagingTemplate.convertAndSend("/topic/chat/" + chatMessage.getRoomId(), chatMessage);

    }


    /**
     * 채팅방 메시지 조회
     */
    public List<ChatMessage> getChatMessages(String roomId, int page, int size) {
        // 페이징 처리 및 정렬 (최신 메시지부터)
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        return chatMessageRepository.findByRoomIdOrderByCreatedAtDesc(roomId, pageRequest);
    }


}