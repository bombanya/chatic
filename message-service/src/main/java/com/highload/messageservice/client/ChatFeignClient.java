package com.highload.messageservice.client;

import com.highload.messageservice.models.MessageOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@ReactiveFeignClient("chat-service")
public interface ChatFeignClient {
    @GetMapping("/auth/{chatId}/{personId}/{operation}")
    Mono<Void> authorizeOperation(
            @PathVariable UUID chatId,
            @PathVariable UUID personId,
            @PathVariable MessageOperation operation
    );
}
