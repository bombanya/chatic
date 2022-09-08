package com.highload.chatic.repository;

import com.highload.chatic.models.MessageContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageContentRepository extends JpaRepository<MessageContent, UUID> {
    @Query(
            value = "select * from MessageContent where message_id in :messageIds",
            nativeQuery = true
    )
    List<MessageContent> findAllByMessageId(Iterable<UUID> messageIds);

    Optional<MessageContent> findByMessageId(UUID id);
}