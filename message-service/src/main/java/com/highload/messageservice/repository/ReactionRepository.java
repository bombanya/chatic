package com.highload.messageservice.repository;

import com.highload.messageservice.models.Reaction;
import com.highload.messageservice.models.ReactionId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReactionRepository extends JpaRepository<Reaction, ReactionId> {
    Page<Reaction> findByReactionId_MessageId(UUID messageId, Pageable pageable);
}
