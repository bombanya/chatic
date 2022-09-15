package com.highload.chatic.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
public class PersonalChatControllerIT {
    private static final String USER31 = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMzEiLCJzY29wZSI6IlVTRVIifQ.DU1nndJ1zvG5mhm_mpCj2Dqir3N51x4kCi8kRU1fg1Yvy79HFh3DqJRHP9C65WHfpOXZ6qHQPQd-7epr8Wkf_GBdEj3l72XoUdqs23GWjoO21f6Tz7cwgCxietYUI9MEBTJGn7myJoyXDXJHalR2bTcAh6MVfQ3OZmDWE1soJyrpuwKy-IPFTB72hcOpjKcFnESn0_RMSWHoTdAuSQlpr57y1x9kmQPn3p2vyjtQT7K1Ji7qfclPf8LENi86xEqPPIIqipojCg3bfl3HYKXr7oUEXKuZXDRxtfPgY2R1wX3_lbafuqvjHAH94Waj76JK2PxL8WbJth8FYcVWPKigdQ";
    private static final String USER32 = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMzIiLCJzY29wZSI6IlVTRVIifQ.iNo-bQvFyYGz0AJcvv9EkKBXB8V0X20ZkllKDkKU-uKCDgIYTYCV11wCCxQ_OeYH0lkKq326fUCgHksR_2_zTAdDCt_bE2wfvGjiXCTOqJC5lW5d7J-aZYPiwmGadS0VONBafLlJEcd9FERvK23Yw5FCuiYuvMJA-qcwjxd8zcn60E5yDyTqLJFXSU9UNZREBlyoIbdb8vtVRP3HaNUks5DZGnwQm4pcZiv_FChkqgmneX12XrZgoZRZ3D959kPvmnrg5YNpyhmvVG7vLvO3JBoJnvovggTYlP1FncOQvL1Blp9vmEeOXRqmX8mSfiSiZHSb9SFpD0RC4OtwoC9P-A";


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getPersonalChat() {
        final String baseUrl = "http://localhost:8080/personal-chats";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("username", "user33");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);


        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(), HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getPersonalChat_notExisting() {
        final String baseUrl = "http://localhost:8080/personal-chats";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("username", "user31");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);


        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(), HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getPersonalChat_notAuthorized() {
        final String baseUrl = "http://localhost:8080/personal-chats";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("username", "user33");

        HttpHeaders headers = new HttpHeaders();


        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(), HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }


    @Test
    public void getPersonalChats() {
        final String baseUrl = "http://localhost:8080/personal-chats";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("page", "0")
                .queryParam("size", "1");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);


        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                builder.build().encode().toUri(), HttpMethod.GET, requestEntity, Map.class);

        assertTrue( (int) response.getBody().get("totalPages") > 0);
    }

    @Test
    public void getPersonalChats_noChats() {
        final String baseUrl = "http://localhost:8080/personal-chats";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("page", "0")
                .queryParam("size", "1");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER31);


        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                builder.build().encode().toUri(), HttpMethod.GET, requestEntity, Map.class);

        assertTrue( (int) response.getBody().get("totalPages") == 0);
    }

    @Test
    public void getPersonalChats_notAuthorized() {
        final String baseUrl = "http://localhost:8080/personal-chats";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("page", "0")
                .queryParam("size", "1");

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                builder.build().encode().toUri(), HttpMethod.GET, requestEntity, Map.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }




    @Test
    public void addPersonalChat() {
        final String baseUrl = "http://localhost:8080/personal-chats";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("username", "user33");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER31);


        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(), HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void addPersonalChat_existing() {
        final String baseUrl = "http://localhost:8080/personal-chats";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("username", "user33");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);


        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(), HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addPersonalChat_notAuthorized() {
        final String baseUrl = "http://localhost:8080/personal-chats";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("username", "user33");

        HttpHeaders headers = new HttpHeaders();


        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(), HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
