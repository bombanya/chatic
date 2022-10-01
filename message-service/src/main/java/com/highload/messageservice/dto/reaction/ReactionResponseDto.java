package com.highload.messageservice.dto.reaction;

import com.highload.messageservice.models.Emoji;

import java.util.UUID;

public record ReactionResponseDto(UUID authorId, Emoji emoji) {

}
