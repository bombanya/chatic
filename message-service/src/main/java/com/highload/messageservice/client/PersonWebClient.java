package com.highload.messageservice.client;

import com.highload.messageservice.config.AppConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PersonWebClient {
    private final WebClient webClient;

    public PersonWebClient(WebClient webClient,
                         AppConfig applicationConfig) {
        this.webClient = webClient
                .mutate()
                .baseUrl(applicationConfig.getUserServiceUrl())
                .build();
    }

    public Mono<Person> getUser(String userId) {
        return webClient
                .get()
                .uri("/v1/users/{userId}", userId)
                .retrieve()
//        .onStatus(HttpStatus::is4xxClientError, resp -> Mono.error(new RuntimeException("ERROR 4xx: " + resp.body(BodyExtractors.toMono(Map.class)).toString())))
//        .onStatus(HttpStatus::is5xxServerError, resp -> Mono.error(new RuntimeException("5xx")))
                .bodyToMono(Person.class);
    }
}
