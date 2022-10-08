package com.highload.messageservice.client;

import com.highload.messageservice.client.shared.PersonResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient("person-service")
public interface PersonFeignClient {
    @GetMapping(path = "/persons/{username}")
    Mono<PersonResponseDto> getPerson(@PathVariable(value = "username") String username);
}
