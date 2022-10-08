package com.highload.chatservice.service;

import com.highload.chatservice.models.MessageOperation;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ChatService {

    Mono<?> authorizeOperation(UUID chatId, UUID personId, MessageOperation operation);


}
