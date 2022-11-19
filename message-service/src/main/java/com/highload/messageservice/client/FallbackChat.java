package com.highload.messageservice.client;

import com.highload.messageservice.models.ChatOperation;
import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
class FallbackChat implements ChatFeignClient {

    @Override
    public Mono<Void> authorizeOperation(UUID chatId,
                                         UUID personId,
                                         ChatOperation operation) {
        return Mono.error(new NoFallbackAvailableException("Person-client not available", new RuntimeException()));
    }
}