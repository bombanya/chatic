package com.highload.chatic.dto.message;

import com.highload.chatic.dto.PageResponseDto;
import com.highload.chatic.models.Message;
import com.highload.chatic.models.MessageContent;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MessagePageResponseDto extends PageResponseDto<MessageResponseDto> {
    public MessagePageResponseDto(List<MessageResponseDto> page, boolean hasNext) {
        super(page, hasNext);
    }

    public static MessagePageResponseDto fromMessage(
            Map<UUID, Message> messages, List<MessageContent> contents, boolean hasNext
    ) {
        return new MessagePageResponseDto(
                contents.stream().map(content -> {
                    var message = messages.get(content.getMessageId());
                    return MessageResponseDto.fromMessage(message, content);
                }).toList(),
                hasNext
        );
    }
}
