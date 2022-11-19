package com.highload.chatservice.client;

import com.highload.chatservice.client.shared.PersonResponseDto;
import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
class Fallback implements PersonFeignClient {

    @Override
    public Mono<PersonResponseDto> getPerson(String username) {
        return Mono.error(new NoFallbackAvailableException("Person-client not available", new RuntimeException()));
    }

    @Override
    public Mono<PersonResponseDto> getPerson(UUID userId) {
        return Mono.error(new NoFallbackAvailableException("Person-client not available", new RuntimeException()));
    }

}