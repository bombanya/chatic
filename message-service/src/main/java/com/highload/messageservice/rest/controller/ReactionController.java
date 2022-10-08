package com.highload.messageservice.rest.controller;

import com.highload.messageservice.dto.reaction.ReactionRequestDto;
import com.highload.messageservice.models.Reaction;
import com.highload.messageservice.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
    public Mono<PageImpl<Reaction>> getReactions(
            @PathVariable UUID messageId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Principal principal
    ) {
        return service.getReactions(principal.getName(), messageId, PageRequest.of(page, size));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Reaction> addReaction(
            @PathVariable UUID messageId,
            @RequestBody @Valid ReactionRequestDto reactionRequestDto,
            Principal principal
    ) {
        return service.addReaction(principal.getName(), messageId, reactionRequestDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteReaction(
            @PathVariable UUID messageId,
            Principal principal
    ) {
        return service.deleteReaction(principal.getName(), messageId);
    }
}
