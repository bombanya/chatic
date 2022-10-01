package com.highload.messageservice.service;

import com.highload.messageservice.dto.PageResponseDto;
import com.highload.messageservice.dto.reaction.ReactionRequestDto;
import com.highload.messageservice.dto.reaction.ReactionResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReactionService {
    void addReaction(String username, UUID messageId, ReactionRequestDto reactionRequestDto);

    void deleteReaction(String username, UUID messageId);

    PageResponseDto<ReactionResponseDto> getReactions(String username, UUID messageId, Pageable pageable);
}
