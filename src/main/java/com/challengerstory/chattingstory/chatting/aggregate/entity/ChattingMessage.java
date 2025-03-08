package com.challengerstory.chattingstory.chatting.aggregate.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="message")
public class ChattingMessage {

    @Id
    private String id;
    private String roomId;
    private String senderId;
    private String content;
    private LocalDateTime createdAt;
    private MessageType type;

}
