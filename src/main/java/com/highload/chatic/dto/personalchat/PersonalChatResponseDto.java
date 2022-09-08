package com.highload.chatic.dto.personalchat;

import java.util.UUID;

public record PersonalChatResponseDto(
        UUID id,
        UUID person1Id,
        UUID person2Id
) {
}
