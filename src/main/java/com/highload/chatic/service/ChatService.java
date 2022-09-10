package com.highload.chatic.service;

import com.highload.chatic.models.MessageOperation;

import java.util.UUID;

public interface ChatService {

    void authorizeOperation(UUID chatId, UUID personId, MessageOperation operation);


}
