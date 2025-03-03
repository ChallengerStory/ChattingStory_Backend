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
public class ChattingRoom {
    @Id
    private String id;

    private String title;
    private List<String> participants;

}
