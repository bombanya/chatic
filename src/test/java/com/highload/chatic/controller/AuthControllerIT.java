package com.highload.chatic.controller;

import com.highload.chatic.ChaticApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthControllerIT extends ChaticApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    public ResponseEntity<Map> sendAuth(String username, String password) throws URISyntaxException {
        final String baseUrl = "http://localhost:8080/login";
        URI uri = new URI(baseUrl);

        return restTemplate.withBasicAuth(username, password).postForEntity(uri, null, Map.class);
    }


    @Test
    @Sql({"/sql/auth.sql"})
    public void login() throws URISyntaxException {
        ResponseEntity<Map> response = sendAuth("admin1", "admin1");
        assertEquals(HttpStatus.OK, response.getStatusCode());

        //return response.getHeaders().get("Authorization").get(0);
    }

}
