package com.highload.messageservice.client;

import com.highload.messageservice.models.MessageOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient("chat-service")
public interface ChatFeignClient {
    @GetMapping("/personal-chats/{chatId}/{personId}/{operation}")
    void authorizeOperation(
            @PathVariable UUID chatId,
            @PathVariable UUID personId,
            @PathVariable MessageOperation operation
    );
}
