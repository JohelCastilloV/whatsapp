package com.xcale.whatsapp.mapper;

import com.xcale.whatsapp.model.Chat;
import com.xcale.whatsapp.model.ChatRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    Chat toChat(ChatRequest source);
}
