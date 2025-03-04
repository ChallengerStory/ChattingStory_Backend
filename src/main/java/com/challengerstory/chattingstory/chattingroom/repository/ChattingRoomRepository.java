package com.challengerstory.chattingstory.chattingroom.repository;

import com.challengerstory.chattingstory.chattingroom.aggregate.entity.ChattingRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChattingRoomRepository extends MongoRepository<ChattingRoom, String> {

}
