package com.challengerstory.chattingstory.chatting.command.respository;

import com.challengerstory.chattingstory.chatting.aggregate.entity.ChatMessage;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.net.ContentHandler;
import java.util.List;


@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByRoomIdOrderByCreatedAtDesc(String roomId, Pageable pageable);
    List<ChatMessage> findBySenderId(String senderId);

}
