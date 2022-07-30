package com.xcale.whatsapp.repository;

import com.xcale.whatsapp.model.Message;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MessageRepository extends ReactiveCrudRepository<Message,String> {
    @Tailable
    Flux<Message> findAllByChatId(String chatId);

    Flux<Message> findByChatId(String chatId);
}
