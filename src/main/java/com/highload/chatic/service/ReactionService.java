package com.highload.chatic.service;

import com.highload.chatic.dto.PageResponseDto;
import com.highload.chatic.dto.reaction.ReactionRequestDto;
import com.highload.chatic.dto.reaction.ReactionResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReactionService {
    void addReaction(String username, UUID messageId, ReactionRequestDto reactionRequestDto);

    void deleteReaction(String username, UUID messageId);

    PageResponseDto<ReactionResponseDto> getReactions(String username, UUID messageId, Pageable pageable);
}
