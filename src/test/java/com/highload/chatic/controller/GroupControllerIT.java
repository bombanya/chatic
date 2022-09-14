package com.highload.chatic.controller;

import com.highload.chatic.ChaticApplicationTests;
import com.highload.chatic.dto.GroupRoleDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroupControllerIT extends ChaticApplicationTests {
    private static final String USER30 = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMzAiLCJzY29wZSI6IlVTRVIifQ.eF12LUn_drCbEdp0NpV-nv0sAQDoZXTWPFw6WFdi5EsPGpkMJzOQndPi90BOfsazu9ukj2Xmjew2dtJxGTuR0sd2RRH_UQkNNNzJ7oaul3wLWfHXc5T7Zx3zWOsbWR-4PIozBkk1Tv99HlNckunp-80q9q994Y_-s7zL77b62aR7WqamxiGnm4_WCaSCk4MU_FEngiUorLGy0mhPRMyStBBZK6dw71yeq5lHfSxQeU_i7avruo12-xK_gDomZAMOR41P4fzmsl6CwJnZGL1Bl_7rjIahiHMS6LYUtKzB6K4a0sZ-iEyjzQ2VDmRdSrYrF6RNvHNWWH07TKADj-GJjw";
    private static final String USER31 = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMzEiLCJzY29wZSI6IlVTRVIifQ.DU1nndJ1zvG5mhm_mpCj2Dqir3N51x4kCi8kRU1fg1Yvy79HFh3DqJRHP9C65WHfpOXZ6qHQPQd-7epr8Wkf_GBdEj3l72XoUdqs23GWjoO21f6Tz7cwgCxietYUI9MEBTJGn7myJoyXDXJHalR2bTcAh6MVfQ3OZmDWE1soJyrpuwKy-IPFTB72hcOpjKcFnESn0_RMSWHoTdAuSQlpr57y1x9kmQPn3p2vyjtQT7K1Ji7qfclPf8LENi86xEqPPIIqipojCg3bfl3HYKXr7oUEXKuZXDRxtfPgY2R1wX3_lbafuqvjHAH94Waj76JK2PxL8WbJth8FYcVWPKigdQ";
    private static final String USER32 = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMzIiLCJzY29wZSI6IlVTRVIifQ.iNo-bQvFyYGz0AJcvv9EkKBXB8V0X20ZkllKDkKU-uKCDgIYTYCV11wCCxQ_OeYH0lkKq326fUCgHksR_2_zTAdDCt_bE2wfvGjiXCTOqJC5lW5d7J-aZYPiwmGadS0VONBafLlJEcd9FERvK23Yw5FCuiYuvMJA-qcwjxd8zcn60E5yDyTqLJFXSU9UNZREBlyoIbdb8vtVRP3HaNUks5DZGnwQm4pcZiv_FChkqgmneX12XrZgoZRZ3D959kPvmnrg5YNpyhmvVG7vLvO3JBoJnvovggTYlP1FncOQvL1Blp9vmEeOXRqmX8mSfiSiZHSb9SFpD0RC4OtwoC9P-A";
    private static final String USER33 = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMzMiLCJzY29wZSI6IlVTRVIifQ.Pl1nY_qMCLGG1rhtOqPuQnfrp5so20q79pxRSR1Mh1uWXpwkAPA1jgz6W0iVruc-fZRn_Jsd5VCQS1XJyNsxG1bYhzbWl20_cL1G70I_JZ6bb0r9yfqdYQb1imZ7y7Fp7jsqyG5eVUGmxdEGyeyd1TqzaHgUzAS4RsbOcmSfmjfcPDHUhy1CxSYhCPboW7_gPZy7z8OCSNKFoP8vGhlXPTpMZU5PXPqfwCWEN8V645DMjLPKMNfbrU_RPcDaPRRF4khw6M6Vc8FmfvFtKsKcrLLyh5vjQICOvtFvjTVjHrO8632rK2zw43pOxZCe_WzrJSrdroOA4lhTFhgFnNTTGQ";
    private static final String ADMIN = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInNjb3BlIjoiQURNSU4ifQ.ovzR5PlKzwhZ-sZNs11EENZPRDAQpeMwRuC_YTIYWEwZoAzudcNcgcilosfzEVemU4Dyk53WpHTyz7aKRE1dGGFdwZv0COb81VnmuxADay-x5fDpMV8SEb6SwAy63PUlxupQb_-aLJ3oW3qk8h-ncP9DYspdALZrZIsOzyWPo6oX-Jh-G-1T9SSES3dLsjplg3wqtE53isciUlC_pfu-PCQk1GehO7QoPPAf6nFi6uHz3zg4P6c8T3W4EEn8q1Y42PI27WCNK4LgKuhWzg4rQf5wSg7Bz2neYsma-uwpIFol9SeFK5QyOsZIIZhyWFQB_fRqB1IDN2W9MPXjnrxK-g";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void addGroup() throws URISyntaxException {
        final String baseUrl = "http://localhost:8080/groups";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER30);


        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void addGroup_notAuthorized() throws URISyntaxException {
        final String baseUrl = "http://localhost:8080/groups";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }




    @Test
    public void getMembers() throws URISyntaxException {
        String groupId = "8bb5e6f2-0247-44f0-8bab-a6790df871d4";
        final String baseUrl = "http://localhost:8080/groups/" + groupId + "/members";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER31);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getMembers_notInGroup() throws URISyntaxException {
        String groupId = "8bb5e6f2-0247-44f0-8bab-a6790df871d4";
        final String baseUrl = "http://localhost:8080/groups/" + groupId + "/members";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER33);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void getMembers_notAuthorized() throws URISyntaxException {
        String groupId = "8bb5e6f2-0247-44f0-8bab-a6790df871d4";
        final String baseUrl = "http://localhost:8080/groups/" + groupId + "/members";

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }



    @Test
    public void getMember() throws URISyntaxException {
        String groupId = "8bb5e6f2-0247-44f0-8bab-a6790df871d4";
        String memberId = "0966480c-a94f-42dd-8a95-40ca402b7fd2";
        final String baseUrl = "http://localhost:8080/groups/" + groupId + "/members/" + memberId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER31);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getMember_notInGroup() throws URISyntaxException {
        String groupId = "8bb5e6f2-0247-44f0-8bab-a6790df871d4";
        String memberId = "0966480c-a94f-42dd-8a95-40ca402b7fd2";
        final String baseUrl = "http://localhost:8080/groups/" + groupId + "/members/" + memberId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER33);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void getMember_forSearchNotInGroup() throws URISyntaxException {
        String groupId = "8bb5e6f2-0247-44f0-8bab-a6790df871d4";
        String memberId = "8df618ef-887f-410f-8608-3323c9ab71ae";
        final String baseUrl = "http://localhost:8080/groups/" + groupId + "/members/" + memberId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER31);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getMember_notAuthorized() throws URISyntaxException {
        String groupId = "8bb5e6f2-0247-44f0-8bab-a6790df871d4";
        String memberId = "0966480c-a94f-42dd-8a95-40ca402b7fd2";
        final String baseUrl = "http://localhost:8080/groups/" + groupId + "/members/" + memberId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }




    @Test
    public void addMember() throws URISyntaxException {
        String groupId = "8bb5e6f2-0247-44f0-8bab-a6790df871d4";
        String memberId = "0966480c-a94f-42dd-8a95-40ca402b7fd2";
        final String baseUrl = "http://localhost:8080/groups/" + groupId + "/members/" + memberId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER31);

        GroupRoleDto groupRoleDto = new GroupRoleDto(true, true, false);

        HttpEntity<GroupRoleDto> requestEntity = new HttpEntity<>(groupRoleDto,headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void addMember_noRights() throws URISyntaxException {
        String groupId = "8bb5e6f2-0247-44f0-8bab-a6790df871d4";
        String memberId = "8df618ef-887f-410f-8608-3323c9ab71ae";
        final String baseUrl = "http://localhost:8080/groups/" + groupId + "/members/" + memberId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER30);

        GroupRoleDto groupRoleDto = new GroupRoleDto(true, true, false);

        HttpEntity<GroupRoleDto> requestEntity = new HttpEntity<>(groupRoleDto,headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void addMember_notAuthorized() throws URISyntaxException {
        String groupId = "8bb5e6f2-0247-44f0-8bab-a6790df871d4";
        String memberId = "8df618ef-887f-410f-8608-3323c9ab71ae";
        final String baseUrl = "http://localhost:8080/groups/" + groupId + "/members/" + memberId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();

        GroupRoleDto groupRoleDto = new GroupRoleDto(true, true, false);

        HttpEntity<GroupRoleDto> requestEntity = new HttpEntity<>(groupRoleDto,headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.POST, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }


    @Test
    public void deleteMember() throws URISyntaxException {
        String groupId = "8bb5e6f2-0247-44f0-8bab-a6790df871d4";
        String memberId = "54dbaf5b-101c-4958-8857-877cdd312b59";
        final String baseUrl = "http://localhost:8080/groups/" + groupId + "/members/" + memberId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER31);

        GroupRoleDto groupRoleDto = new GroupRoleDto(true, true, false);

        HttpEntity<GroupRoleDto> requestEntity = new HttpEntity<>(groupRoleDto,headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteMember_noRights() throws URISyntaxException {
        String groupId = "8bb5e6f2-0247-44f0-8bab-a6790df871d4";
        String memberId = "1ced10d0-a0b2-42c7-b9c6-d8e3b4946bcb";
        final String baseUrl = "http://localhost:8080/groups/" + groupId + "/members/" + memberId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER30);

        GroupRoleDto groupRoleDto = new GroupRoleDto(true, true, false);

        HttpEntity<GroupRoleDto> requestEntity = new HttpEntity<>(groupRoleDto,headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void deleteMember_notExisting() throws URISyntaxException {
        String groupId = "8bb5e6f2-0247-44f0-8bab-a6790df871d4";
        String memberId = "54dbaf5b-101c-4958-8857-000000000000";
        final String baseUrl = "http://localhost:8080/groups/" + groupId + "/members/" + memberId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", USER31);

        GroupRoleDto groupRoleDto = new GroupRoleDto(true, true, false);

        HttpEntity<GroupRoleDto> requestEntity = new HttpEntity<>(groupRoleDto,headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteMember_notAuthorized() throws URISyntaxException {
        String groupId = "8bb5e6f2-0247-44f0-8bab-a6790df871d4";
        String memberId = "54dbaf5b-101c-4958-8857-877cdd312b59";
        final String baseUrl = "http://localhost:8080/groups/" + groupId + "/members/" + memberId;

        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();

        GroupRoleDto groupRoleDto = new GroupRoleDto(true, true, false);

        HttpEntity<GroupRoleDto> requestEntity = new HttpEntity<>(groupRoleDto,headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
