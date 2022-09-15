package com.highload.chatic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;


@Component
public class AuthMaster {

    @Autowired
    private TestRestTemplate restTemplate;

    public <T> HttpEntity<T> createRequestWithAuthHeader(String username, String password,
                                                         String baseUrl, T body) {
        baseUrl += "/login";

        var response = restTemplate.withBasicAuth(username, password)
                .postForEntity(baseUrl, null, Object.class);
        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + response.getHeaders().getFirst("Authorization"));
        return new HttpEntity<>(body, headers);
    }
}
