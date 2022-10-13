package com.highload.chatservice.client;

import com.highload.chatservice.client.shared.PersonResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@ReactiveFeignClient(name = "person-service", fallback = Fallback.class)
public interface PersonFeignClient {
    @GetMapping("/persons/byusername/{username}")
    Mono<PersonResponseDto> getPerson(@PathVariable String username);

    @GetMapping("/persons/byid/{userId}")
    Mono<PersonResponseDto> getPerson(@PathVariable UUID userId);
}
