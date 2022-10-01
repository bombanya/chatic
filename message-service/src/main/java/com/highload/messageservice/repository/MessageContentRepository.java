package com.highload.messageservice.repository;

import com.highload.messageservice.models.MessageContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageContentRepository extends JpaRepository<MessageContent, UUID> {
    List<MessageContent> findByMessageIdIn(Collection<UUID> messageId);

    Optional<MessageContent> findByMessageId(UUID id);

    @Modifying
    @Query("update MessageContent mc set mc.text = :text where mc.messageId = :messageId")
    void updateMessageContent(UUID messageId, String text);
}