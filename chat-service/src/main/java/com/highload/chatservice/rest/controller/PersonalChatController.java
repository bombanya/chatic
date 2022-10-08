package com.highload.chatservice.rest.controller;

import com.highload.chatservice.dto.personalchat.PersonalChatResponseDto;
import com.highload.chatservice.models.MessageOperation;
import com.highload.chatservice.models.PersonalChat;
import com.highload.chatservice.service.ChatService;
import com.highload.chatservice.service.PersonalChatService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("personal-chats")
public class PersonalChatController {

    private final PersonalChatService service;
    private final ChatService chatService;

    @GetMapping
    public Mono<ResponseEntity<PersonalChatResponseDto>> getPersonalChat(
            @RequestParam String username,
            Principal principal
    ) {
        return service.getChat(principal.getName(), username)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //кажется, логичнее вынести в общий контроллер для чатов
    @GetMapping(params = {"page", "size"})
    @ResponseStatus(HttpStatus.OK)
    @SneakyThrows
    public Mono<ResponseEntity<PageImpl<PersonalChat>>> getPersonalChats(
            @RequestParam int page,
            @RequestParam int size,
            Principal principal
    ) {
        return service.getAllChats(principal.getName(), PageRequest.of(page, size))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<PersonalChat>> addPersonalChat(
            @RequestParam String username,
            Principal principal
    ) {
        return service.addChat(principal.getName(), username)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
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
