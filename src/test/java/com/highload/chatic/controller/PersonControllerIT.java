package com.highload.chatic.controller;

import com.highload.chatic.ChaticApplicationTests;
import com.highload.chatic.dto.person.PersonRequestDto;
import com.highload.chatic.models.AuthRoleName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PersonControllerIT extends ChaticApplicationTests {
    private static final String USER31 = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMzEiLCJzY29wZSI6IlVTRVIifQ.DU1nndJ1zvG5mhm_mpCj2Dqir3N51x4kCi8kRU1fg1Yvy79HFh3DqJRHP9C65WHfpOXZ6qHQPQd-7epr8Wkf_GBdEj3l72XoUdqs23GWjoO21f6Tz7cwgCxietYUI9MEBTJGn7myJoyXDXJHalR2bTcAh6MVfQ3OZmDWE1soJyrpuwKy-IPFTB72hcOpjKcFnESn0_RMSWHoTdAuSQlpr57y1x9kmQPn3p2vyjtQT7K1Ji7qfclPf8LENi86xEqPPIIqipojCg3bfl3HYKXr7oUEXKuZXDRxtfPgY2R1wX3_lbafuqvjHAH94Waj76JK2PxL8WbJth8FYcVWPKigdQ";
    private static final String USER32 = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMzIiLCJzY29wZSI6IlVTRVIifQ.iNo-bQvFyYGz0AJcvv9EkKBXB8V0X20ZkllKDkKU-uKCDgIYTYCV11wCCxQ_OeYH0lkKq326fUCgHksR_2_zTAdDCt_bE2wfvGjiXCTOqJC5lW5d7J-aZYPiwmGadS0VONBafLlJEcd9FERvK23Yw5FCuiYuvMJA-qcwjxd8zcn60E5yDyTqLJFXSU9UNZREBlyoIbdb8vtVRP3HaNUks5DZGnwQm4pcZiv_FChkqgmneX12XrZgoZRZ3D959kPvmnrg5YNpyhmvVG7vLvO3JBoJnvovggTYlP1FncOQvL1Blp9vmEeOXRqmX8mSfiSiZHSb9SFpD0RC4OtwoC9P-A";
    private static final String ADMIN = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInNjb3BlIjoiQURNSU4ifQ.ovzR5PlKzwhZ-sZNs11EENZPRDAQpeMwRuC_YTIYWEwZoAzudcNcgcilosfzEVemU4Dyk53WpHTyz7aKRE1dGGFdwZv0COb81VnmuxADay-x5fDpMV8SEb6SwAy63PUlxupQb_-aLJ3oW3qk8h-ncP9DYspdALZrZIsOzyWPo6oX-Jh-G-1T9SSES3dLsjplg3wqtE53isciUlC_pfu-PCQk1GehO7QoPPAf6nFi6uHz3zg4P6c8T3W4EEn8q1Y42PI27WCNK4LgKuhWzg4rQf5wSg7Bz2neYsma-uwpIFol9SeFK5QyOsZIIZhyWFQB_fRqB1IDN2W9MPXjnrxK-g";
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void addPerson() throws URISyntaxException {
        final String baseUrl = "http://localhost:8080/persons";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", ADMIN);

        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setUsername("user_test");
        personRequestDto.setPassword("user_test");
        personRequestDto.setBio("bio");
        personRequestDto.setAuthRole(AuthRoleName.USER);
        HttpEntity<PersonRequestDto> requestEntity = new HttpEntity<>(personRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void addPerson_existing() throws URISyntaxException {
        final String baseUrl = "http://localhost:8080/persons";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", ADMIN);

        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setUsername("admin1");
        personRequestDto.setPassword("user_test");
        personRequestDto.setBio("bio");
        personRequestDto.setAuthRole(AuthRoleName.USER);
        HttpEntity<PersonRequestDto> requestEntity = new HttpEntity<>(personRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addPerson_notAuthorized() throws URISyntaxException {
        final String baseUrl = "http://localhost:8080/persons";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();

        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setUsername("user_test");
        personRequestDto.setPassword("user_test");
        personRequestDto.setBio("bio");
        personRequestDto.setAuthRole(AuthRoleName.USER);
        HttpEntity<PersonRequestDto> requestEntity = new HttpEntity<>(personRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void addPerson_notAdmin() throws URISyntaxException {
        final String baseUrl = "http://localhost:8080/persons";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMzEiLCJzY29wZSI6IlVTRVIifQ.DU1nndJ1zvG5mhm_mpCj2Dqir3N51x4kCi8kRU1fg1Yvy79HFh3DqJRHP9C65WHfpOXZ6qHQPQd-7epr8Wkf_GBdEj3l72XoUdqs23GWjoO21f6Tz7cwgCxietYUI9MEBTJGn7myJoyXDXJHalR2bTcAh6MVfQ3OZmDWE1soJyrpuwKy-IPFTB72hcOpjKcFnESn0_RMSWHoTdAuSQlpr57y1x9kmQPn3p2vyjtQT7K1Ji7qfclPf8LENi86xEqPPIIqipojCg3bfl3HYKXr7oUEXKuZXDRxtfPgY2R1wX3_lbafuqvjHAH94Waj76JK2PxL8WbJth8FYcVWPKigdQ");

        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setUsername("user_test");
        personRequestDto.setPassword("user_test");
        personRequestDto.setBio("bio");
        personRequestDto.setAuthRole(AuthRoleName.USER);
        HttpEntity<PersonRequestDto> requestEntity = new HttpEntity<>(personRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }




    @Test
    public void getPerson() throws URISyntaxException {
        String name = "admin1";
        final String baseUrl = "http://localhost:8080/persons/" + name;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", ADMIN);

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
        headers.set("Authorization", ADMIN);

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




    @Test
    public void deletePerson_admin() throws URISyntaxException {
        String name = "user32";

        final String baseUrl = "http://localhost:8080/persons/" + name;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", ADMIN);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deletePerson_thisPerson() throws URISyntaxException {
        String name = "user31";

        final String baseUrl = "http://localhost:8080/persons/" + name;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER31);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deletePerson_notExisting() throws URISyntaxException {
        String name = "asdf";

        final String baseUrl = "http://localhost:8080/persons/" + name;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", ADMIN);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deletePerson_notAuthorized() throws URISyntaxException {
        String name = "user31";

        final String baseUrl = "http://localhost:8080/persons/" + name;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void deletePerson_notAdminNotThisPerson() throws URISyntaxException {
        String name = "user31";

        final String baseUrl = "http://localhost:8080/persons/" + name;
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
