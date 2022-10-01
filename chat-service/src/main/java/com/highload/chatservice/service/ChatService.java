package com.highload.chatservice.service;

import com.highload.chatservice.models.MessageOperation;

import java.util.UUID;

public interface ChatService {

    void authorizeOperation(UUID chatId, UUID personId, MessageOperation operation);


}
