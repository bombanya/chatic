package com.highload.chatic.rest.controller;

import com.highload.chatic.dto.PageResponseDto;
import com.highload.chatic.dto.reaction.ReactionRequestDto;
import com.highload.chatic.dto.reaction.ReactionResponseDto;
import com.highload.chatic.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("messages/{messageId}/reactions")
@RequiredArgsConstructor
public class ReactionController {

    private final ReactionService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageResponseDto<ReactionResponseDto> getReactions (
            @PathVariable UUID messageId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Principal principal
    ) {
        return service.getReactions(principal.getName(), messageId, PageRequest.of(page, size));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addReaction(
            @PathVariable UUID messageId,
            @RequestBody @Valid ReactionRequestDto reactionRequestDto,
            Principal principal
    ) {
        service.addReaction(principal.getName(), messageId, reactionRequestDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteReaction(
            @PathVariable UUID messageId,
            Principal principal
    ) {
        service.deleteReaction(principal.getName(), messageId);
    }
}
