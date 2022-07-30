package com.xcale.whatsapp.model;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "messages")
@Data
public class Message {

    @Id
    private String id;

    @CreatedDate
    private Instant createdDate;

    @DBRef
    private Chat chat;

    @DBRef
    private User sender;

    private String content;
}
