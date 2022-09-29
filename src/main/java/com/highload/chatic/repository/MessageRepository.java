package com.highload.chatic.repository;

import com.highload.chatic.models.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    Page<Message> findAllByChatIdOrderByTimestampDesc(UUID chatId, Pageable pageable);

    Page<Message> findAllByReplyIdOrderByTimestampDesc(UUID replyId, Pageable pageable);
}
