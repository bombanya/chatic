package com.highload.chatic.controller;

import com.highload.chatic.ChaticApplicationTests;
import com.highload.chatic.dto.message.MessageRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageControllerIT extends ChaticApplicationTests {
    private static final String USER31 = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMzEiLCJzY29wZSI6IlVTRVIifQ.DU1nndJ1zvG5mhm_mpCj2Dqir3N51x4kCi8kRU1fg1Yvy79HFh3DqJRHP9C65WHfpOXZ6qHQPQd-7epr8Wkf_GBdEj3l72XoUdqs23GWjoO21f6Tz7cwgCxietYUI9MEBTJGn7myJoyXDXJHalR2bTcAh6MVfQ3OZmDWE1soJyrpuwKy-IPFTB72hcOpjKcFnESn0_RMSWHoTdAuSQlpr57y1x9kmQPn3p2vyjtQT7K1Ji7qfclPf8LENi86xEqPPIIqipojCg3bfl3HYKXr7oUEXKuZXDRxtfPgY2R1wX3_lbafuqvjHAH94Waj76JK2PxL8WbJth8FYcVWPKigdQ";
    private static final String USER32 = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMzIiLCJzY29wZSI6IlVTRVIifQ.iNo-bQvFyYGz0AJcvv9EkKBXB8V0X20ZkllKDkKU-uKCDgIYTYCV11wCCxQ_OeYH0lkKq326fUCgHksR_2_zTAdDCt_bE2wfvGjiXCTOqJC5lW5d7J-aZYPiwmGadS0VONBafLlJEcd9FERvK23Yw5FCuiYuvMJA-qcwjxd8zcn60E5yDyTqLJFXSU9UNZREBlyoIbdb8vtVRP3HaNUks5DZGnwQm4pcZiv_FChkqgmneX12XrZgoZRZ3D959kPvmnrg5YNpyhmvVG7vLvO3JBoJnvovggTYlP1FncOQvL1Blp9vmEeOXRqmX8mSfiSiZHSb9SFpD0RC4OtwoC9P-A";
    private static final String USER33 = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMzMiLCJzY29wZSI6IlVTRVIifQ.Pl1nY_qMCLGG1rhtOqPuQnfrp5so20q79pxRSR1Mh1uWXpwkAPA1jgz6W0iVruc-fZRn_Jsd5VCQS1XJyNsxG1bYhzbWl20_cL1G70I_JZ6bb0r9yfqdYQb1imZ7y7Fp7jsqyG5eVUGmxdEGyeyd1TqzaHgUzAS4RsbOcmSfmjfcPDHUhy1CxSYhCPboW7_gPZy7z8OCSNKFoP8vGhlXPTpMZU5PXPqfwCWEN8V645DMjLPKMNfbrU_RPcDaPRRF4khw6M6Vc8FmfvFtKsKcrLLyh5vjQICOvtFvjTVjHrO8632rK2zw43pOxZCe_WzrJSrdroOA4lhTFhgFnNTTGQ";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void addMessage() throws URISyntaxException {
        final String baseUrl = "http://localhost:8080/messages";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);

        MessageRequestDto messageRequestDto = new MessageRequestDto(
                "text1",
                UUID.fromString("4ae20120-419b-4679-8d13-68b9e917572c")
        );

        HttpEntity<MessageRequestDto> requestEntity = new HttpEntity<>(messageRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void addMessage_noChat() throws URISyntaxException {
        final String baseUrl = "http://localhost:8080/messages";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);

        MessageRequestDto messageRequestDto = new MessageRequestDto(
                "text1",
                UUID.fromString("4ae20120-419b-4679-8d13-000000000000")
        );

        HttpEntity<MessageRequestDto> requestEntity = new HttpEntity<>(messageRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void addMessage_noAccess() throws URISyntaxException {
        final String baseUrl = "http://localhost:8080/messages";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER31);

        MessageRequestDto messageRequestDto = new MessageRequestDto(
                "text1",
                UUID.fromString("4ae20120-419b-4679-8d13-68b9e917572c")
        );

        HttpEntity<MessageRequestDto> requestEntity = new HttpEntity<>(messageRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void addMessage_notAuthorized() throws URISyntaxException {
        final String baseUrl = "http://localhost:8080/messages";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();

        MessageRequestDto messageRequestDto = new MessageRequestDto(
                "text1",
                UUID.fromString("4ae20120-419b-4679-8d13-68b9e917572c")
        );

        HttpEntity<MessageRequestDto> requestEntity = new HttpEntity<>(messageRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }


    @Test
    public void addReply() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/replies";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);

        MessageRequestDto messageRequestDto = new MessageRequestDto(
                "text1",
                UUID.fromString("4ae20120-419b-4679-8d13-68b9e917572c")
        );

        HttpEntity<MessageRequestDto> requestEntity = new HttpEntity<>(messageRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void addReply_noMessage() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-000000000000";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/replies";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);

        MessageRequestDto messageRequestDto = new MessageRequestDto(
                "text1",
                UUID.fromString("4ae20120-419b-4679-8d13-68b9e917572c")
        );

        HttpEntity<MessageRequestDto> requestEntity = new HttpEntity<>(messageRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addReply_noChat() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/replies";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);

        MessageRequestDto messageRequestDto = new MessageRequestDto(
                "text1",
                UUID.fromString("4ae20120-419b-4679-8d13-000000000000")
        );

        HttpEntity<MessageRequestDto> requestEntity = new HttpEntity<>(messageRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void addReply_noAccess() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/replies";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER31);

        MessageRequestDto messageRequestDto = new MessageRequestDto(
                "text1",
                UUID.fromString("4ae20120-419b-4679-8d13-68b9e917572c")
        );

        HttpEntity<MessageRequestDto> requestEntity = new HttpEntity<>(messageRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void addReply_notAuthorized() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/replies";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();

        MessageRequestDto messageRequestDto = new MessageRequestDto(
                "text1",
                UUID.fromString("4ae20120-419b-4679-8d13-68b9e917572c")
        );

        HttpEntity<MessageRequestDto> requestEntity = new HttpEntity<>(messageRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void updateMessage() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);

        MessageRequestDto messageRequestDto = new MessageRequestDto(
                "text2",
                UUID.fromString("4ae20120-419b-4679-8d13-68b9e917572c")
        );

        HttpEntity<MessageRequestDto> requestEntity = new HttpEntity<>(messageRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.PUT, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void updateMessage_notAuthor() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER33);

        MessageRequestDto messageRequestDto = new MessageRequestDto(
                "text2",
                UUID.fromString("4ae20120-419b-4679-8d13-68b9e917572c")
        );

        HttpEntity<MessageRequestDto> requestEntity = new HttpEntity<>(messageRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.PUT, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateMessage_noMessage() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-000000000000";
        final String baseUrl = "http://localhost:8080/messages/" + messageId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);

        MessageRequestDto messageRequestDto = new MessageRequestDto(
                "text2",
                UUID.fromString("4ae20120-419b-4679-8d13-68b9e917572c")
        );

        HttpEntity<MessageRequestDto> requestEntity = new HttpEntity<>(messageRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.PUT, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateMessage_notAuthorized() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();

        MessageRequestDto messageRequestDto = new MessageRequestDto(
                "text2",
                UUID.fromString("4ae20120-419b-4679-8d13-68b9e917572c")
        );

        HttpEntity<MessageRequestDto> requestEntity = new HttpEntity<>(messageRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.PUT, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }



    @Test
    public void deleteMessage() throws URISyntaxException {
        String messageId = "c2b57b8e-146a-4f7b-b207-a17b8d24744b";
        final String baseUrl = "http://localhost:8080/messages/" + messageId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER33);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteMessage_notAuthor() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER33);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteMessage_noMessage() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-000000000000";
        final String baseUrl = "http://localhost:8080/messages/" + messageId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER33);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteMessage_notAuthorized() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }



    @Test
    public void getMessage() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getMessage_noAccess() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER31);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void getMessage_notAuthorized() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }



    @Test
    public void getMessages() {
        final String baseUrl = "http://localhost:8080/messages";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("chatId", "4ae20120-419b-4679-8d13-68b9e917572c");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(), HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getMessages_noChat() {
        final String baseUrl = "http://localhost:8080/messages";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("chatId", "4ae0120-419b-4679-8d13-68b9e917572c");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(), HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getMessages_noAccess() {
        final String baseUrl = "http://localhost:8080/messages";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("chatId", "4ae20120-419b-4679-8d13-68b9e917572c");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER31);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(), HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void getMessages_notAuthorized() {
        final String baseUrl = "http://localhost:8080/messages";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("chatId", "4ae20120-419b-4679-8d13-68b9e917572c");

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(), HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }



    @Test
    public void getReplies() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/replies";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getReplies_noMessage() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-000000000000";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/replies";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER32);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getReplies_noAccess() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/replies";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER31);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void getReplies_notAuthorized() throws URISyntaxException {
        String messageId = "cdf6d98f-e42d-41ce-be0c-6431af9f7dda";
        final String baseUrl = "http://localhost:8080/messages/" + messageId + "/replies";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
