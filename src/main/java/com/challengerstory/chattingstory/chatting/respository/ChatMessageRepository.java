package com.challengerstory.chattingstory.chatting.respository;

import com.challengerstory.chattingstory.chatting.aggregate.entity.ChattingMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChatMessageRepository extends MongoRepository<ChattingMessage, String> {
    List<ChattingMessage> findByRoomIdOrderByCreatedAtDesc(String roomId, Pageable pageable);
    List<ChattingMessage> findBySenderId(String senderId);

}
