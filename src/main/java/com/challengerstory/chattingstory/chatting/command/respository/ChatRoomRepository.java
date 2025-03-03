package com.challengerstory.chattingstory.chatting.command.respository;

import com.challengerstory.chattingstory.chatting.aggregate.entity.ChattingRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChattingRoom, String> {

}
