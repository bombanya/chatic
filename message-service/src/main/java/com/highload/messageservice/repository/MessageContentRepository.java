package com.highload.messageservice.repository;

import com.highload.messageservice.models.MessageContent;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.UUID;

public interface MessageContentRepository extends R2dbcRepository<MessageContent, UUID> {
    Flux<MessageContent> findByMessageIdIn(Collection<UUID> messageId);

    Mono<MessageContent> findByMessageId(UUID id);

    @Modifying
    @Query("update messagecontent set text = :text where message_id = :messageId")
    Mono<Void> updateMessageContent(UUID messageId, String text);
}