package com.xcale.whatsapp.security;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@NoArgsConstructor
@EqualsAndHashCode
public class ChatAuthority implements GrantedAuthority {
    private String authority;

    public ChatAuthority(String chatId) {
        this.authority = "CHAT_%s_MOD".formatted(chatId);
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public String toString() {
        return getAuthority();
    }
}
