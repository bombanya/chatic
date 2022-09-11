package com.highload.chatic.dto.reaction;

import com.highload.chatic.models.Emoji;

import java.util.UUID;

public record ReactionResponseDto(UUID authorId, Emoji emoji) {

}
