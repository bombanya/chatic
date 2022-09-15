package com.highload.chatic.controller;

import com.highload.chatic.dto.person.PersonRequestDto;
import com.highload.chatic.models.AuthRoleName;
import org.junit.jupiter.api.BeforeAll;
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
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:delete_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
        baseUrl = "http://localhost:" + port;
    }

    @Test
    public void addPerson() {

        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setUsername("user_test");
        personRequestDto.setPassword("user_test");
        personRequestDto.setBio("bio");
        personRequestDto.setAuthRole(AuthRoleName.USER);
        var requestEntity = authMaster
                .createRequestWithAuthHeader("admin", "admin", baseUrl, personRequestDto);

        ResponseEntity<?> response = restTemplate.exchange(baseUrl + "/persons",
                HttpMethod.POST, requestEntity, Object.class);
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
                .createRequestWithAuthHeader("admin", "admin", baseUrl, personRequestDto);

        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/persons",
                HttpMethod.POST, requestEntity, String.class);
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

        ResponseEntity<String> response = restTemplate.exchange(baseUrl  + "/persons",
                HttpMethod.POST, requestEntity, String.class);
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
                .createRequestWithAuthHeader("user1", "user1", baseUrl, personRequestDto);

        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/persons",
                HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void getPerson() {
        String name = "/persons/admin";

        HttpEntity<?> requestEntity = authMaster
                .createRequestWithAuthHeader("user1", "user1", baseUrl, null);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + name, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getPerson_notExisting() {
        String name = "/persons/asdf";

        HttpEntity<?> requestEntity = authMaster
                .createRequestWithAuthHeader("user1", "user1", baseUrl, null);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + name, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void getPerson_notAuthorized() {
        String name = "/persons/admin";
        ResponseEntity<?> response = restTemplate.getForEntity(baseUrl + name, Object.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void deletePerson_admin() {
        String name = "/persons/user1";

        HttpEntity<?> requestEntity = authMaster
                .createRequestWithAuthHeader("admin", "admin", baseUrl, null);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + name, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deletePerson_thisPerson() {
        String name = "/persons/user1";

        HttpEntity<?> requestEntity = authMaster
                .createRequestWithAuthHeader("user1", "user1", baseUrl, null);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + name, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deletePerson_notExisting() {
        String name = "/persons/asdf";

        HttpEntity<?> requestEntity = authMaster
                .createRequestWithAuthHeader("admin", "admin", baseUrl, null);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + name, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deletePerson_notAuthorized() {
        String name = "/persons/user1";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + name, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void deletePerson_notAdminNotThisPerson() {
        String name = "/persons/user1";

        HttpEntity<Void> requestEntity = authMaster
                .createRequestWithAuthHeader("user2", "user2", baseUrl, null);

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + name, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
