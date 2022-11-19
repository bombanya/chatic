package com.highload.chatservice.service;

import com.highload.chatservice.models.ChatOperation;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ChatService {

    Mono<Void> authorizeOperation(UUID chatId, UUID personId, ChatOperation operation);

}
