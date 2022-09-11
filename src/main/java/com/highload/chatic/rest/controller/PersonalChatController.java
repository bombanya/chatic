package com.highload.chatic.rest.controller;

import com.highload.chatic.dto.PageResponseDto;
import com.highload.chatic.dto.personalchat.PersonalChatResponseDto;
import com.highload.chatic.service.PersonalChatService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("personal-chats")
public class PersonalChatController {

    private final PersonalChatService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PersonalChatResponseDto getPersonalChat(
            @RequestParam String username,
            Principal principal
    ) {
        return service.getChat(principal.getName(), username);
    }

    //кажется, логичнее вынести в общий контроллер для чатов
    @GetMapping(params = {"page", "size"})
    @ResponseStatus(HttpStatus.OK)
    @SneakyThrows
    public PageResponseDto<PersonalChatResponseDto> getPersonalChats(
            @RequestParam int page,
            @RequestParam int size,
            Principal principal
    ) {
        return service.getAllChats(principal.getName(), PageRequest.of(page, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonalChatResponseDto addPersonalChat(
            @RequestParam String username,
            Principal principal
    ) {
        return service.addChat(principal.getName(), username);
    }

}
