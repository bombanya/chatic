package com.highload.messageservice.service;

import com.highload.messageservice.dto.message.MessageRequestDto;
import com.highload.messageservice.dto.message.MessageResponseDto;
import com.highload.messageservice.models.MessageContent;
import com.highload.messageservice.models.MessageOperation;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MessageService {

    Mono<MessageContent> updateMessage(String username, UUID messageId, MessageRequestDto messageRequestDto);

    Mono<Void> deleteMessage(String username, UUID messageId);

    Mono<Void> addMessage(String username, MessageRequestDto messageRequestDto);

    Mono<Void> addReply(String username, UUID replyId, MessageRequestDto messageRequestDto);

    Mono<MessageResponseDto> getMessage(String username, UUID messageId);

    Mono<PageImpl<Object>> getChatMessages(String username, UUID chatId, Pageable pageable);

    Mono<PageImpl<Object>> getMessageReplies(String username, UUID messageId, Pageable pageable);

    void authorizeOperationOnMessage(UUID messageId, UUID personId, MessageOperation operation);
}
