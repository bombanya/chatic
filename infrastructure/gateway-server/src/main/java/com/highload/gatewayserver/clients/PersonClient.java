package com.highload.gatewayserver.clients;

import com.highload.gatewayserver.dto.PersonAuthDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "person-service")
public interface PersonClient {

    @GetMapping("/persons-auth/{username}")
    Mono<PersonAuthDto> getPerson(@PathVariable String username);
}
