package com.highload.chatic.repository;

import com.highload.chatic.models.Reaction;
import com.highload.chatic.models.ReactionId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, ReactionId> {
    @Query(
            "select r from Reaction r " +
                    "where r.reactionId.messageId = :messageId"
    )
    Page<Reaction> findReactions(UUID messageId, Pageable pageable);
}