package com.challengerstory.chattingstory.chatting.command.application.service;


import com.challengerstory.chattingstory.chatting.aggregate.entity.ChattingMessage;
import com.challengerstory.chattingstory.chatting.aggregate.entity.MessageType;
import com.challengerstory.chattingstory.chatting.command.respository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChattingService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;


    /**
     * 채팅 메시지 전송
     *
     * @return
     */
    public ChattingMessage sendMessage(ChattingMessage chattingMessage) {
        // 메시지 타입이 지정되지 않은 경우 CHAT으로 설정
        if (chattingMessage.getType() == null) {
            chattingMessage.setType(MessageType.CHAT);
        }

        // 타임스탬프 설정
        chattingMessage.setCreatedAt(LocalDateTime.now());

        // 메시지 저장
        chattingMessage = chatMessageRepository.save(chattingMessage);

        // WebSocket을 통해 메시지 전송
        messagingTemplate.convertAndSend("/topic/chat/" + chattingMessage.getRoomId(), chattingMessage);
        log.debug("chattingMessage: {}", chattingMessage);
        return chattingMessage;
    }


    /**
     * 채팅방 메시지 조회
     */
    public List<ChattingMessage> getChatMessages(String roomId, int page, int size) {
        // 페이징 처리 및 정렬 (최신 메시지부터)
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        return chatMessageRepository.findByRoomIdOrderByCreatedAtDesc(roomId, pageRequest);
    }


}