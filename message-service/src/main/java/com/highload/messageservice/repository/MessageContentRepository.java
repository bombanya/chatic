package com.highload.messageservice.repository;

import com.highload.messageservice.models.MessageContent;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageContentRepository extends R2dbcRepository<MessageContent, UUID> {
    Mono<List<MessageContent>> findByMessageIdIn(Collection<UUID> messageId);

    Mono<Optional<MessageContent>> findByMessageId(UUID id);

    @Modifying
    @Query("update messagecontent set text = :text where message_id = :messageId")
    void updateMessageContent(UUID messageId, String text);
}