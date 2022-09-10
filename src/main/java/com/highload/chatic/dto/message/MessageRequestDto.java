package com.highload.chatic.dto.message;

import com.highload.chatic.dto.validation.AddRequest;
import com.highload.chatic.dto.validation.UpdateRequest;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.UUID;


public record MessageRequestDto(
        @Null(groups = AddRequest.class)
        @NotNull(groups = UpdateRequest.class)
        UUID id,
        UUID replyId,
        @NotEmpty(message = "Не указан текст сообщения")
        String textContent
) {
}
