package com.highload.chatic.controller;

import com.highload.chatic.dto.person.PersonRequestDto;
import com.highload.chatic.models.AuthRoleName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@Sql("classpath:data.sql")
public class PersonControllerIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private AuthMaster authMaster;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/persons";
    }

    @Test
    public void addPerson() {

        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setUsername("user_test");
        personRequestDto.setPassword("user_test");
        personRequestDto.setBio("bio");
        personRequestDto.setAuthRole(AuthRoleName.USER);
        var requestEntity = authMaster
                .createRequestWithAuthHeader("admin", "admin", personRequestDto);

        ResponseEntity<?> response = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, Object.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void addPerson_existing() {
        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setUsername("admin");
        personRequestDto.setPassword("user_test");
        personRequestDto.setBio("bio");
        personRequestDto.setAuthRole(AuthRoleName.USER);
        HttpEntity<PersonRequestDto> requestEntity = authMaster
                .createRequestWithAuthHeader("admin", "admin", personRequestDto);

        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addPerson_notAuthorized() {
        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setUsername("user_test");
        personRequestDto.setPassword("user_test");
        personRequestDto.setBio("bio");
        personRequestDto.setAuthRole(AuthRoleName.USER);
        HttpEntity<PersonRequestDto> requestEntity = new HttpEntity<>(personRequestDto);

        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void addPerson_notAdmin() {
        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setUsername("user_test");
        personRequestDto.setPassword("user_test");
        personRequestDto.setBio("bio");
        personRequestDto.setAuthRole(AuthRoleName.USER);
        HttpEntity<PersonRequestDto> requestEntity = authMaster
                .createRequestWithAuthHeader("user1", "user1", personRequestDto);

        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void getPerson() {
        String name = "/admin";

        HttpEntity<?> requestEntity = authMaster
                .createRequestWithAuthHeader("user1", "user1", null);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + name, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getPerson_notExisting() {
        String name = "/asdf";

        HttpEntity<?> requestEntity = authMaster
                .createRequestWithAuthHeader("user1", "user1", null);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + name, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void getPerson_notAuthorized() {
        String name = "/admin";
        ResponseEntity<?> response = restTemplate.getForEntity(baseUrl + name, Object.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void deletePerson_admin() {
        String name = "/user1";

        HttpEntity<?> requestEntity = authMaster
                .createRequestWithAuthHeader("admin", "admin", null);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + name, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deletePerson_thisPerson() {
        String name = "/user1";

        HttpEntity<?> requestEntity = authMaster
                .createRequestWithAuthHeader("user1", "user1", null);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + name, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deletePerson_notExisting() {
        String name = "/asdf";

        HttpEntity<?> requestEntity = authMaster
                .createRequestWithAuthHeader("admin", "admin", null);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + name, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deletePerson_notAuthorized() {
        String name = "/user1";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + name, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void deletePerson_notAdminNotThisPerson() {
        String name = "/user1";

        HttpEntity<Void> requestEntity = authMaster
                .createRequestWithAuthHeader("user2", "user2", null);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + name, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
