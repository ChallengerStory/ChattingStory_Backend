package com.challengerstory.chattingstory.chatting.aggregate.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="chattingroom")
public class ChatRoom {
    @Id
    private String chattingRoomId;

    private String title;
    private List<String> participants;

}
