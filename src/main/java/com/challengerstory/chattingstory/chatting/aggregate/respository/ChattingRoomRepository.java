package com.challengerstory.chattingstory.chatting.aggregate.respository;

import com.challengerstory.chattingstory.chatting.aggregate.entity.ChattingRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChattingRoomRepository  extends MongoRepository<ChattingRoom, String> {

}
