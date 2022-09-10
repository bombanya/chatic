package com.highload.chatic.service;

import com.highload.chatic.dto.PageResponseDto;
import com.highload.chatic.dto.ReactionDto;
import com.highload.chatic.exception.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReactionService {
    void addReaction(String username, UUID messageId, ReactionDto reactionDto) throws ResourceNotFoundException;

    void deleteReaction(String username, UUID id) throws ResourceNotFoundException;

    PageResponseDto<ReactionDto> getReactions(String username, UUID id, Pageable pageable) throws ResourceNotFoundException;
}
