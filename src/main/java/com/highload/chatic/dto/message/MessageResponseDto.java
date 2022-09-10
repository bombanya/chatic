package com.highload.chatic.dto.message;

import com.highload.chatic.models.Message;
import com.highload.chatic.models.MessageContent;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record MessageResponseDto(
        UUID id,
        UUID chatId,
        UUID authorId,
        long timestamp,
        UUID replyId,
        String textContent
) {
    public static MessageResponseDto fromMessageWithContent(Message message, MessageContent content) {
        return new MessageResponseDto(
                message.getId(),
                message.getChatId(),
                message.getAuthorId(),
                message.getTimestamp().getTime(),
                message.getReplyId(),
                content.getText()
        );
    }

    public static List<MessageResponseDto> fromMessagesWithContent(
            Map<UUID, Message> messages, List<MessageContent> contents
    ) {
        return contents.stream().map(content -> {
            var message = messages.get(content.getMessageId());
            return MessageResponseDto.fromMessageWithContent(message, content);
        }).toList();
    }
}
