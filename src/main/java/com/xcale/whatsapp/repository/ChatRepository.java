package com.xcale.whatsapp.repository;

import com.xcale.whatsapp.model.Chat;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ChatRepository extends ReactiveCrudRepository<Chat, String> {

    @Tailable
    Flux<Chat> findAllByMembersAndEnableIsTrue(String userId);

    Flux<Chat> findByMembersContainingAndEnableIsTrue(String userId);

}
