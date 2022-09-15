package com.highload.chatic.controller;

import org.junit.jupiter.api.AfterEach;
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
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:delete_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthControllerIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @AfterEach
    void tearDown() {}

    public ResponseEntity<?> sendAuth(String username, String password) {
        String baseUrl = "http://localhost:" + port + "/login";

        return restTemplate.withBasicAuth(username, password)
                .postForEntity(baseUrl, null, Object.class);
    }

    @Test
    public void login() {
        var response = sendAuth("admin", "admin");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void loginIncorrect() {
        var response = sendAuth("admin", "admin1");
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
