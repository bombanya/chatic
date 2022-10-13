package com.highload.messageservice.client;

import com.highload.messageservice.client.shared.PersonResponseDto;
import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
class FallbackPerson implements PersonFeignClient {

    @Override
    public Mono<PersonResponseDto> getPerson(String username) {
        throw new NoFallbackAvailableException("Person-client not available", new RuntimeException());
    }
}