package com.challengerstory.chattingstory.chatting.command.respository;

import com.challengerstory.chattingstory.chatting.aggregate.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

}
