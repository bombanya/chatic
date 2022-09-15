package com.highload.chatic.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
public class AuthControllerIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    public ResponseEntity<?> sendAuth(String username, String password) {
        String baseUrl = "http://localhost:" + port + "/login";

        return restTemplate.withBasicAuth(username, password)
                .postForEntity(baseUrl, null, Object.class);
    }

    @Test
    @Sql("classpath:data.sql")
    public void login() {
        var response = sendAuth("admin", "admin");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    @Sql("classpath:data.sql")
    public void loginIncorrect() {
        var response = sendAuth("admin", "admin1");
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
