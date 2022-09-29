package com.highload.chatic.repository;

import com.highload.chatic.models.Reaction;
import com.highload.chatic.models.ReactionId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReactionRepository extends JpaRepository<Reaction, ReactionId> {
    Page<Reaction> findByReactionId_MessageId(UUID messageId, Pageable pageable);
}
