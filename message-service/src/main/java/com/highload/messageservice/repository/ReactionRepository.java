package com.highload.messageservice.repository;

import com.highload.messageservice.models.Reaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ReactionRepository extends R2dbcRepository<Reaction, UUID> {
   Flux<Reaction> findByMessageId(UUID messageId, Pageable pageable);
   Mono<Reaction> findByMessageIdAndPersonId(UUID messageId, UUID personId);
   Mono<Void> deleteByMessageIdAndPersonId(UUID messageId, UUID personId);
   Mono<Long> countByMessageId(UUID messageId);
}
