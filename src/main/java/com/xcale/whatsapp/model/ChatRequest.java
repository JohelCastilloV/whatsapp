package com.xcale.whatsapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ChatRequest {
    @JsonIgnore
    private String id;
    private String name;
}
