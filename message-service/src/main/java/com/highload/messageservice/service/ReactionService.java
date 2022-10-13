package com.highload.messageservice.service;

import com.highload.messageservice.dto.reaction.ReactionRequestDto;
import com.highload.messageservice.dto.reaction.ReactionResponseDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ReactionService {
    Mono<ReactionResponseDto> addReaction(String username, UUID messageId, ReactionRequestDto reactionRequestDto);

    Mono<Void> deleteReaction(String username, UUID messageId);

    Mono<PageImpl<ReactionResponseDto>> getReactions(String username, UUID messageId, Pageable pageable);
}
