package com.challengerstory.chattingstory.chatting.aggregate.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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
