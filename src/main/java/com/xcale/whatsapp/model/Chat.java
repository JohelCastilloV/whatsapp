package com.xcale.whatsapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document(collection = "chat")
public class Chat {

    @Id
    private String id;
    private String name;
    @DBRef
    private Set<User> members;
    private boolean enable;
}
