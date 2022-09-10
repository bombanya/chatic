package com.highload.chatic.dto.message;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;


public record MessageRequestDto(
        @NotEmpty(message = "Не указан текст сообщения")
        String textContent,

        @NotNull(message = "Не указан чат сообщения")
        UUID chatId
) {
}
