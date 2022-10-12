package com.highload.chatservice.rest.controller;

import com.highload.chatservice.dto.PageResponseDto;
import com.highload.chatservice.dto.personalchat.PersonalChatResponseDto;
import com.highload.chatservice.models.PersonalChat;
import com.highload.chatservice.service.PersonalChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/personal-chats")
public class PersonalChatController {

    private final PersonalChatService service;

    @GetMapping
    public Mono<PersonalChatResponseDto> getPersonalChat(@RequestParam(name = "username") String username1,
                                                         @RequestHeader("USERNAME") String username2) {
        return service.getChat(username1, username2);
    }

    @GetMapping
    public Mono<PageResponseDto<PersonalChat>> getPersonalChats(@RequestParam int page,
                                                                @RequestParam int size,
                                                                @RequestHeader("USERNAME") String username) {
        return service.getAllChats(username, PageRequest.of(page, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PersonalChatResponseDto> addPersonalChat(@RequestParam(name = "username") String username1,
                                                         @RequestHeader("USERNAME") String username2) {
        return service.addChat(username1, username2);
    }


}
