package com.xcale.whatsapp.service;

import com.xcale.whatsapp.mapper.ChatMapper;
import com.xcale.whatsapp.mapper.UserMapper;
import com.xcale.whatsapp.model.Chat;
import com.xcale.whatsapp.model.ChatRequest;
import com.xcale.whatsapp.model.User;
import com.xcale.whatsapp.model.UserRequest;
import com.xcale.whatsapp.repository.ChatRepository;
import com.xcale.whatsapp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    private final ChatMapper chatMapper;
    private final UserMapper userMapper;

    public Flux<Chat> findAllChatsForCurrentUser() {
        return SecurityUtil.getPrincipal()
                .flatMapMany(principal -> chatRepository.findByMembersContainingAndEnableIsTrue(principal.getId()))
                .log();
    }

    @PostAuthorize("@webSecurity.addChatAuthority(authentication, #chatRequest)")
    public Mono<Chat> saveChat(ChatRequest chatRequest) {
        Chat chat = chatMapper.toChat(chatRequest);
        chat.setEnable(true);
        Set<User> members = Objects.requireNonNullElseGet(chat.getMembers(), HashSet::new);
        return SecurityUtil.getPrincipal()
                .doOnNext(principal -> members.add(principal.user()))
                .doOnNext(principal -> chat.setMembers(members))
                .flatMap(principal -> chatRepository.save(chat))
                .doOnNext(chat1 -> chatRequest.setId(chat1.getId()));
    }

    @PreAuthorize("@webSecurity.hasChatAuthority(authentication, #chatId) " +
            "and @webSecurity.removeChatAuthority(authentication, #chatId)")
    public Mono<Void> deleteChat(String chatId) {
        return chatRepository.findById(chatId).flatMap(chat -> {
            chat.setEnable(false);
            return chatRepository.save(chat);
        }).then();
    }

    public Flux<Chat> streamNewChatsByUserId(String userId) {
        return chatRepository.findAllByMembersAndEnableIsTrue(userId);
    }
    public Flux<User> findAllMembers(String chatId) {
        return chatRepository
                .findById(chatId)
                .flatMapMany(chat -> Flux.fromIterable(chat.getMembers()));
    }

    public Mono<Chat> saveMember(String chatId, UserRequest userRequest) {
        User member = userMapper.toUser(userRequest);
        return chatRepository
                .findById(chatId)
                .doOnNext(chat -> chat.getMembers().add(member))
                .flatMap(chatRepository::save);
    }

    @PreAuthorize("@webSecurity.hasChatAuthority(authentication, #chatId) or #userId == principal.id")
    public Mono<Void> deleteMember(String chatId, String userId) {
        return chatRepository
                .findById(chatId)
                .filter(chat -> chat.getMembers().removeIf(member -> member.getId().equals(userId)))
                .flatMap(chatRepository::save)
                .then();
    }
}
