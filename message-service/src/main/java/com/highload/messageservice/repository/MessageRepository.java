package com.highload.messageservice.repository;

import com.highload.messageservice.models.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface MessageRepository extends R2dbcRepository<Message, UUID> {
    Flux<Message> findAllByChatIdOrderByTimestampDesc(UUID chatId, Pageable pageable);

    Flux<Message> findAllByReplyIdOrderByTimestampDesc(UUID replyId, Pageable pageable);
}
