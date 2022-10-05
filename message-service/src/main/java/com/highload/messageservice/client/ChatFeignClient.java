package com.highload.messageservice.client;

import com.highload.messageservice.models.MessageOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient("chat-service")
public interface ChatFeignClient {
    @GetMapping("/{chatId}/{personId}/{operation}")
    void authorizeOperation(
            @PathVariable(value = "chatId") UUID chatId,
            @PathVariable(value = "personId") UUID personId,
            @PathVariable(value = "operation") MessageOperation operation
    );
}
