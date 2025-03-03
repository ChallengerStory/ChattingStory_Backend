package com.challengerstory.chattingstory.chatting.aggregate.entity;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="chattingroom")
public class ChattingRoom {
    @Id
    private String chattingRoomId;

    private String name;
    private List<Long> members;

}
