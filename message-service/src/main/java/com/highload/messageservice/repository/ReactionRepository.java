package com.highload.messageservice.repository;

import com.highload.messageservice.models.Reaction;
import com.highload.messageservice.models.ReactionId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;


import java.util.UUID;

public interface ReactionRepository extends R2dbcRepository<Reaction, ReactionId> {
   Flux<Reaction> findByReactionId_MessageId(UUID messageId, Pageable pageable);
}
