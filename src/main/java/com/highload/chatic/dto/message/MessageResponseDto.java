package com.highload.chatic.dto.message;

import com.highload.chatic.models.Message;
import com.highload.chatic.models.MessageContent;

import java.util.UUID;

public record MessageResponseDto(
        UUID id,
        UUID chatId,
        UUID authorId,
        long timestamp,
        UUID replyId,
        String textContent
) {
    public static MessageResponseDto fromMessage(Message message, MessageContent content) {
        return new MessageResponseDto(
                message.getId(),
                message.getChatId(),
                message.getAuthorId(),
                message.getTimestamp().getTime(),
                message.getReplyId(),
                content.getText()
        );
    }
}
