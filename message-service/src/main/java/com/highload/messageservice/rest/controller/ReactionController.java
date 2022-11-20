package com.highload.messageservice.rest.controller;

import com.highload.messageservice.dto.PageResponseDto;
import com.highload.messageservice.dto.reaction.ReactionRequestDto;
import com.highload.messageservice.dto.reaction.ReactionResponseDto;
import com.highload.messageservice.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("messages/{messageId}/reactions")
@RequiredArgsConstructor
public class ReactionController {

    private final ReactionService service;

    @GetMapping
    public Mono<PageResponseDto<ReactionResponseDto>> getReactions(@PathVariable UUID messageId,
                                                                   @RequestParam(defaultValue = "0", required = false) int page,
                                                                   @RequestParam(defaultValue = "20" ,required = false) int size,
                                                                   @RequestHeader("USERNAME") String username) {
        return service.getReactions(username, messageId, PageRequest.of(page, size));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ReactionResponseDto> addReaction(@PathVariable UUID messageId,
                               @RequestBody @Valid ReactionRequestDto reactionRequestDto,
                               @RequestHeader("USERNAME") String username) {
        return service.addReaction(username, messageId, reactionRequestDto);
    }

    @DeleteMapping
    public Mono<Void> deleteReaction(@PathVariable UUID messageId,
                                  @RequestHeader("USERNAME") String username) {
        return service.deleteReaction(username, messageId);
    }
}
