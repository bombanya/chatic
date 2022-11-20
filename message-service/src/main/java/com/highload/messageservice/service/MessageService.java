package com.highload.messageservice.service;

import com.highload.messageservice.dto.PageResponseDto;
import com.highload.messageservice.dto.message.MessageRequestDto;
import com.highload.messageservice.dto.message.MessageResponseDto;
import com.highload.messageservice.models.ChatOperation;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MessageService {

    Mono<Void> updateMessage(String username, UUID messageId, MessageRequestDto messageRequestDto);

    Mono<Void> deleteMessage(String username, UUID messageId);

    Mono<MessageResponseDto> addMessage(String username, MessageRequestDto messageRequestDto);

    Mono<MessageResponseDto> addReply(String username, UUID replyId, MessageRequestDto messageRequestDto);

    Mono<MessageResponseDto> getMessage(String username, UUID messageId);

    Mono<PageResponseDto<MessageResponseDto>> getChatMessages(String username, UUID chatId, Pageable pageable);

    Mono<PageResponseDto<MessageResponseDto>> getMessageReplies(String username, UUID messageId, Pageable pageable);

    Mono<Void> authorizeOperationOnMessage(UUID messageId, UUID personId, ChatOperation operation);
}
