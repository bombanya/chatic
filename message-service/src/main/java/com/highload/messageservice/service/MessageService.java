package com.highload.messageservice.service;

import com.highload.messageservice.dto.message.MessageRequestDto;
import com.highload.messageservice.dto.message.MessageResponseDto;
import com.highload.messageservice.models.MessageOperation;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MessageService {

    Mono<?> updateMessage(String username, UUID messageId, MessageRequestDto messageRequestDto);

    Mono<?> deleteMessage(String username, UUID messageId);

    Mono<?> addMessage(String username, MessageRequestDto messageRequestDto);

    Mono<?> addReply(String username, UUID replyId, MessageRequestDto messageRequestDto);

    Mono<MessageResponseDto> getMessage(String username, UUID messageId);

    Mono<PageImpl<MessageResponseDto>> getChatMessages(String username, UUID chatId, Pageable pageable);

    Mono<PageImpl<MessageResponseDto>> getMessageReplies(String username, UUID messageId, Pageable pageable);

    Mono<?> authorizeOperationOnMessage(UUID messageId, UUID personId, MessageOperation operation);
}
