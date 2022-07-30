package com.xcale.whatsapp.security;

import com.xcale.whatsapp.model.ChatRequest;
import com.xcale.whatsapp.model.Message;
import com.xcale.whatsapp.model.User;
import com.xcale.whatsapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSecurity {
    private final UserRepository userRepository;

    public boolean hasChatAuthority(Authentication authentication, String chatId) {
        return authentication.getAuthorities().contains(new ChatAuthority(chatId));
    }

    public boolean addChatAuthority(Authentication authentication, ChatRequest chat) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.user();
        user.addAuthority(new ChatAuthority(chat.getId()));
        userRepository.save(user).subscribe();
        return true;
    }

    public boolean removeChatAuthority(Authentication authentication, String chatId) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.user();

        if (user.removeAuthority(new ChatAuthority(chatId))) {
            userRepository.save(user).subscribe();
        }

        return true;
    }

    public boolean isMessageSender(Authentication authentication, Message message) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.user();

        return user.getId().equals(message.getSender().getId());
    }
}
