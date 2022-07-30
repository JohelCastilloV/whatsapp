package com.xcale.whatsapp.controller;


import com.xcale.whatsapp.model.*;
import com.xcale.whatsapp.service.ChatService;
import com.xcale.whatsapp.service.MessageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/chats")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final MessageService messageService;

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public Flux<Chat> getAllChats(){
        return chatService.findAllChatsForCurrentUser();
    }

    @PostMapping
    public Mono<Chat> createChat(@RequestBody ChatRequest chatRequest){
        return chatService.saveChat(chatRequest);
    }

    @DeleteMapping("/{chatId}")
    public Mono<Void> deleteChat(@PathVariable String chatId){
        return chatService.deleteChat(chatId);
    }

    @GetMapping(value = "/{userId}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getNewChats(@PathVariable String userId){
        return chatService.streamNewChatsByUserId(userId);
    }

    @GetMapping( "/{chatId}/members")
    public Flux<User> getMembers(@PathVariable String chatId){
        return chatService.findAllMembers(chatId);
    }

    @PostMapping( "/{chatId}/members")
    public Mono<Chat> saveMember(@PathVariable String chatId, @RequestBody UserRequest user){
        return chatService.saveMember(chatId, user);
    }

    @DeleteMapping( "/{chatId}/members/{userId}")
    public Mono<Void> getMembers(@PathVariable String chatId, @PathVariable String userId){
        return chatService.deleteMember(chatId, userId);
    }

    @GetMapping(value = "/{chatId}/messages/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Message> getNewMessagesStream(@PathVariable String chatId){
        return messageService.findAllMessagesByChatIdStream(chatId);
    }

    @GetMapping(value = "/{chatId}/messages")
    public Flux<Message> getNewMessages(@PathVariable String chatId){
        return messageService.findAllMessagesByChatId(chatId);
    }

    @PostMapping("/{chatId}/messages")
    public Mono<Message> saveMessage(@PathVariable String chatId,@RequestBody Message message){
        return messageService.saveMessage(message, chatId);
    }

    @DeleteMapping( "/{chatId}/messages/{messageId}")
    public Mono<Void> deleteMessage(@PathVariable String chatId, @PathVariable String messageId){
        return messageService
                .findById(messageId)
                .flatMap(messageService::deleteMessage);
    }




}
