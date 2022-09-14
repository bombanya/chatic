package com.highload.chatic.controller;

import com.highload.chatic.ChaticApplicationTests;
import com.highload.chatic.dto.person.PersonRequestDto;
import com.highload.chatic.dto.reaction.ReactionRequestDto;
import com.highload.chatic.models.AuthRoleName;
import com.highload.chatic.models.Emoji;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReactionControllerIT extends ChaticApplicationTests {
    private static final String USER31 = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMzEiLCJzY29wZSI6IlVTRVIifQ.DU1nndJ1zvG5mhm_mpCj2Dqir3N51x4kCi8kRU1fg1Yvy79HFh3DqJRHP9C65WHfpOXZ6qHQPQd-7epr8Wkf_GBdEj3l72XoUdqs23GWjoO21f6Tz7cwgCxietYUI9MEBTJGn7myJoyXDXJHalR2bTcAh6MVfQ3OZmDWE1soJyrpuwKy-IPFTB72hcOpjKcFnESn0_RMSWHoTdAuSQlpr57y1x9kmQPn3p2vyjtQT7K1Ji7qfclPf8LENi86xEqPPIIqipojCg3bfl3HYKXr7oUEXKuZXDRxtfPgY2R1wX3_lbafuqvjHAH94Waj76JK2PxL8WbJth8FYcVWPKigdQ";
    private static final String USER32 = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMzIiLCJzY29wZSI6IlVTRVIifQ.iNo-bQvFyYGz0AJcvv9EkKBXB8V0X20ZkllKDkKU-uKCDgIYTYCV11wCCxQ_OeYH0lkKq326fUCgHksR_2_zTAdDCt_bE2wfvGjiXCTOqJC5lW5d7J-aZYPiwmGadS0VONBafLlJEcd9FERvK23Yw5FCuiYuvMJA-qcwjxd8zcn60E5yDyTqLJFXSU9UNZREBlyoIbdb8vtVRP3HaNUks5DZGnwQm4pcZiv_FChkqgmneX12XrZgoZRZ3D959kPvmnrg5YNpyhmvVG7vLvO3JBoJnvovggTYlP1FncOQvL1Blp9vmEeOXRqmX8mSfiSiZHSb9SFpD0RC4OtwoC9P-A";
    private static final String USER33 = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMzMiLCJzY29wZSI6IlVTRVIifQ.Pl1nY_qMCLGG1rhtOqPuQnfrp5so20q79pxRSR1Mh1uWXpwkAPA1jgz6W0iVruc-fZRn_Jsd5VCQS1XJyNsxG1bYhzbWl20_cL1G70I_JZ6bb0r9yfqdYQb1imZ7y7Fp7jsqyG5eVUGmxdEGyeyd1TqzaHgUzAS4RsbOcmSfmjfcPDHUhy1CxSYhCPboW7_gPZy7z8OCSNKFoP8vGhlXPTpMZU5PXPqfwCWEN8V645DMjLPKMNfbrU_RPcDaPRRF4khw6M6Vc8FmfvFtKsKcrLLyh5vjQICOvtFvjTVjHrO8632rK2zw43pOxZCe_WzrJSrdroOA4lhTFhgFnNTTGQ";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getReactions() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/reactions";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getReactions_noMessage() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-000000000000";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/reactions";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getReactions_noAccess() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/reactions";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER31);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void getReactions_notAuthorized() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/reactions";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void addReaction() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/reactions";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);

        ReactionRequestDto reactionRequestDto = new ReactionRequestDto();
        reactionRequestDto.setEmoji(Emoji.LIKE);
        HttpEntity<ReactionRequestDto> requestEntity = new HttpEntity<>(reactionRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.PUT, requestEntity, String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void addReaction_noMessage() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-000000000000";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/reactions";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);

        ReactionRequestDto reactionRequestDto = new ReactionRequestDto();
        reactionRequestDto.setEmoji(Emoji.LIKE);
        HttpEntity<ReactionRequestDto> requestEntity = new HttpEntity<>(reactionRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.PUT, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void addReaction_noAccess() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/reactions";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER31);

        ReactionRequestDto reactionRequestDto = new ReactionRequestDto();
        reactionRequestDto.setEmoji(Emoji.LIKE);
        HttpEntity<ReactionRequestDto> requestEntity = new HttpEntity<>(reactionRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.PUT, requestEntity, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void addReaction_notAuthorized() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/reactions";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();

        ReactionRequestDto reactionRequestDto = new ReactionRequestDto();
        reactionRequestDto.setEmoji(Emoji.LIKE);
        HttpEntity<ReactionRequestDto> requestEntity = new HttpEntity<>(reactionRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.PUT, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }




    @Test
    public void deleteReaction() throws URISyntaxException {
        String messageId = "c2b57b8e-146a-4f7b-b207-a17b8d24744b";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/reactions";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER33);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteReaction_notExisting() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-000000000000";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/reactions";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteReaction_noReaction() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/reactions";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER33);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteReaction_notAuthorized() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/reactions";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
