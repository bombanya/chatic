package com.highload.chatservice.rest.controller;

import com.highload.chatservice.models.MessageOperation;
import com.highload.chatservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class ChatsAuthController {

    private final ChatService chatService;

    @PostMapping("/{chatId}/{personId}/{operation}")
    public Mono<?> authorizeOperation(@PathVariable UUID chatId,
                                      @PathVariable UUID personId,
                                      @PathVariable MessageOperation operation) {
        return chatService.authorizeOperation(chatId, personId, operation);
    }
}
