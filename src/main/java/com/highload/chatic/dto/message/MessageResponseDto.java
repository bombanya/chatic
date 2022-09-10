package com.highload.chatic.dto.message;

import com.highload.chatic.models.Message;
import com.highload.chatic.models.MessageContent;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public record MessageResponseDto(
        UUID id,
        UUID chatId,
        UUID authorId,
        Timestamp timestamp,
        UUID replyId,
        String textContent
) {
    public static MessageResponseDto fromMessageWithContent(Message message, MessageContent content) {
        return new MessageResponseDto(
                message.getId(),
                message.getChatId(),
                message.getAuthorId(),
                message.getTimestamp(),
                message.getReplyId(),
                content.getText()
        );
    }

    public static List<MessageResponseDto> fromMessagesWithContent(
            List<Message> messages, List<MessageContent> contents
    ) {
        var contentMap = contents
                .stream()
                .collect(Collectors.toMap(MessageContent::getMessageId, Function.identity()));
        return messages.stream()
                .map(message -> fromMessageWithContent(message, contentMap.get(message.getId())))
                .toList();
    }
}
