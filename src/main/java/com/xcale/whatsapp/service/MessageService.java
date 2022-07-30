package com.xcale.whatsapp.service;

import com.xcale.whatsapp.model.Chat;
import com.xcale.whatsapp.model.Message;
import com.xcale.whatsapp.repository.MessageRepository;
import com.xcale.whatsapp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    private final ChatService chatService;

    public Mono<Message> findById(String messageId) {
        return messageRepository.findById(messageId);
    }

    public Flux<Message> findAllMessagesByChatIdStream(String chatId) {
        return messageRepository.findAllByChatId(chatId).log();
    }

    public Flux<Message> findAllMessagesByChatId(String chatId) {
        return messageRepository.findByChatId(chatId).log();
    }


    public Mono<Message> saveMessage(Message message, String chatId) {
        Chat chat = new Chat();
        chat.setId(chatId);
        message.setChat(chat);

        return SecurityUtil.getPrincipal()
                .doOnNext(principal -> message.setSender(principal.user()))
                .flatMap(principal -> messageRepository.save(message));
    }

    @PreAuthorize("@webSecurity.isMessageSender(authentication, #message)")
    public Mono<Void> deleteMessage(Message message) {
        return messageRepository.delete(message);
    }
}
