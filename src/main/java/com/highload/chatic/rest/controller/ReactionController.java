package com.highload.chatic.rest.controller;

import com.highload.chatic.dto.PageResponseDto;
import com.highload.chatic.dto.ReactionDto;
import com.highload.chatic.dto.message.MessageRequestDto;
import com.highload.chatic.dto.message.MessageResponseDto;
import com.highload.chatic.dto.validation.AddRequest;
import com.highload.chatic.dto.validation.UpdateRequest;
import com.highload.chatic.service.MessageService;
import com.highload.chatic.service.ReactionService;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

@RestController
@Validated
public class ReactionController {

    private final ReactionService service;

    public ReactionController(ReactionService reactionService) {
        this.service = reactionService;
    }

    @GetMapping(value = "/reaction/{id}", params = {"page", "size"})
    @ResponseStatus(HttpStatus.OK)
    @SneakyThrows
    public PageResponseDto<ReactionDto> getReactions(
            @PathVariable UUID id,
            @RequestParam int page,
            @RequestParam int size,
            Principal principal
    ) {
        return service.getReactions(principal.getName(), id, PageRequest.of(page, size));
    }

    @PostMapping("/reaction/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @SneakyThrows
    public void addReaction(
            @PathVariable UUID id,
            @RequestBody @Valid ReactionDto reactionDto,
            Principal principal
    ) {
        service.addReaction(principal.getName(), id, reactionDto);
    }

    @DeleteMapping("/reaction/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SneakyThrows
    public void deleteReaction(
            @PathVariable UUID id,
            Principal principal
    ) {
        service.deleteReaction(principal.getName(), id);
    }
}
