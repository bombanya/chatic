package com.highload.chatic.dto.personalchat;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record PersonalChatRequestDto(
        @NotNull(message = "Не указан участник чата") UUID person1Id,
        @NotNull(message = "Не указан участник чата") UUID person2Id
) {
}
