package com.highload.chatic.dto.message;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;


public record MessageRequestDto(
        @NotEmpty UUID id,
        @NotEmpty(message = "Не указан чат для сообщения") UUID chatId,
        @NotEmpty(message = "Не указан автор сообщения") UUID authorId,
        UUID replyId,
        @NotEmpty(message = "Не указан текст сообщения") String textContent
) {
}
