package com.highload.chatic.rest.controller;

import com.highload.chatic.dto.PageResponseDto;
import com.highload.chatic.dto.message.MessageRequestDto;
import com.highload.chatic.dto.message.MessageResponseDto;
import com.highload.chatic.dto.validation.AddRequest;
import com.highload.chatic.dto.validation.UpdateRequest;
import com.highload.chatic.service.MessageService;
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
public class MessageController {

    private final MessageService service;

    public MessageController(MessageService messageService) {
        this.service = messageService;
    }

    @GetMapping(value = "/personal-chat/{id}/messages", params = {"page", "size"})
    @ResponseStatus(HttpStatus.OK)
    @SneakyThrows
    public PageResponseDto<MessageResponseDto> getPersonalChatMessages(
            @PathVariable UUID id,
            @RequestParam int page,
            @RequestParam int size,
            Principal principal
    ) {
        return service.getPersonalChatMessages(principal.getName(), id, PageRequest.of(page, size));
    }

    @PutMapping("/personal-chat/{id}/messages")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Validated(UpdateRequest.class)
    @SneakyThrows
    public void updatePersonalChatMessage(
        @PathVariable UUID id,
        @RequestBody @Valid MessageRequestDto messageRequestDto,
        Principal principal
    ) {
        service.updatePersonalChatMessage(principal.getName(), id, messageRequestDto);
    }

    @PostMapping("/personal-chat/{id}/messages")
    @Validated(AddRequest.class)
    @ResponseStatus(HttpStatus.CREATED)
    @SneakyThrows
    public void addPersonalChatMessage(
            @PathVariable UUID id,
            @RequestBody @Valid MessageRequestDto messageRequestDto,
            Principal principal
    ) {
        service.addPersonalChatMessage(principal.getName(), id, messageRequestDto);
    }

    @DeleteMapping("/personal-chat/{chatId}/message/{messageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SneakyThrows
    public void deletePersonalChatMessage(
            @PathVariable UUID chatId,
            @PathVariable UUID messageId,
            Principal principal
    ) {
        service.deletePersonalChatMessage(principal.getName(), chatId, messageId);
    }
}
