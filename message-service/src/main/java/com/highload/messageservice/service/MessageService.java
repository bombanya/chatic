package com.highload.messageservice.service;

import com.highload.messageservice.dto.PageResponseDto;
import com.highload.messageservice.dto.message.MessageRequestDto;
import com.highload.messageservice.dto.message.MessageResponseDto;
import com.highload.messageservice.models.MessageOperation;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MessageService {

    void updateMessage(String username, UUID messageId, MessageRequestDto messageRequestDto);

    void deleteMessage(String username, UUID messageId);

    void addMessage(String username, MessageRequestDto messageRequestDto);

    void addReply(String username, UUID replyId, MessageRequestDto messageRequestDto);

    MessageResponseDto getMessage(String username, UUID messageId);

    PageResponseDto<MessageResponseDto> getChatMessages(String username, UUID chatId, Pageable pageable);

    PageResponseDto<MessageResponseDto> getMessageReplies(String username, UUID messageId, Pageable pageable);

    void authorizeOperationOnMessage(UUID messageId, UUID personId, MessageOperation operation);
}
