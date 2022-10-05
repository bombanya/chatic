package com.highload.chatservice.rest.controller;

import com.highload.chatservice.dto.PageResponseDto;
import com.highload.chatservice.dto.personalchat.PersonalChatResponseDto;
import com.highload.chatservice.models.MessageOperation;
import com.highload.chatservice.service.ChatService;
import com.highload.chatservice.service.PersonalChatService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("personal-chats")
public class PersonalChatController {

    private final PersonalChatService service;
    private final ChatService chatService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PersonalChatResponseDto getPersonalChat(
            @RequestParam String username,
            Principal principal
    ) {
        return service.getChat(principal.getName(), username);
    }

    //кажется, логичнее вынести в общий контроллер для чатов
    @GetMapping(params = {"page", "size"})
    @ResponseStatus(HttpStatus.OK)
    @SneakyThrows
    public PageResponseDto<PersonalChatResponseDto> getPersonalChats(
            @RequestParam int page,
            @RequestParam int size,
            Principal principal
    ) {
        return service.getAllChats(principal.getName(), PageRequest.of(page, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonalChatResponseDto addPersonalChat(
            @RequestParam String username,
            Principal principal
    ) {
        return service.addChat(principal.getName(), username);
    }

    @GetMapping("/{chatId}/{personId}/{operation}")
    public void authorizeOperation(
            @PathVariable UUID chatId,
            @PathVariable UUID personId,
            @PathVariable MessageOperation operation
    ) {
        chatService.authorizeOperation(chatId, personId, operation);
    }
}
