package com.highload.chatic.controller;

import com.highload.chatic.ChaticApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonControllerIT extends ChaticApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getPerson() throws URISyntaxException {
        String name = "admin1";
        final String baseUrl = "http://localhost:8080/persons/" + name;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInNjb3BlIjoiQURNSU4ifQ.ovzR5PlKzwhZ-sZNs11EENZPRDAQpeMwRuC_YTIYWEwZoAzudcNcgcilosfzEVemU4Dyk53WpHTyz7aKRE1dGGFdwZv0COb81VnmuxADay-x5fDpMV8SEb6SwAy63PUlxupQb_-aLJ3oW3qk8h-ncP9DYspdALZrZIsOzyWPo6oX-Jh-G-1T9SSES3dLsjplg3wqtE53isciUlC_pfu-PCQk1GehO7QoPPAf6nFi6uHz3zg4P6c8T3W4EEn8q1Y42PI27WCNK4LgKuhWzg4rQf5wSg7Bz2neYsma-uwpIFol9SeFK5QyOsZIIZhyWFQB_fRqB1IDN2W9MPXjnrxK-g");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getPerson_notExisting() throws URISyntaxException {
        String name = "asdf";
        final String baseUrl = "http://localhost:8080/persons/" + name;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInNjb3BlIjoiQURNSU4ifQ.ovzR5PlKzwhZ-sZNs11EENZPRDAQpeMwRuC_YTIYWEwZoAzudcNcgcilosfzEVemU4Dyk53WpHTyz7aKRE1dGGFdwZv0COb81VnmuxADay-x5fDpMV8SEb6SwAy63PUlxupQb_-aLJ3oW3qk8h-ncP9DYspdALZrZIsOzyWPo6oX-Jh-G-1T9SSES3dLsjplg3wqtE53isciUlC_pfu-PCQk1GehO7QoPPAf6nFi6uHz3zg4P6c8T3W4EEn8q1Y42PI27WCNK4LgKuhWzg4rQf5wSg7Bz2neYsma-uwpIFol9SeFK5QyOsZIIZhyWFQB_fRqB1IDN2W9MPXjnrxK-g");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void getPerson_notAuthorized() throws URISyntaxException {
        String name = "admin1";
        final String baseUrl = "http://localhost:8080/persons/" + name;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
