package com.highload.messageservice.client;

import com.highload.messageservice.models.ChatOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@ReactiveFeignClient(name = "chat-service", fallback = FallbackChat.class)
public interface ChatFeignClient {
    @PostMapping("/auth/{chatId}/{personId}/{operation}")
    Mono<Void> authorizeOperation(@PathVariable UUID chatId,
                                  @PathVariable UUID personId,
                                  @PathVariable ChatOperation operation);
}
