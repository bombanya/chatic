package com.highload.chatic.rest.controller;

import com.highload.chatic.dto.PageResponseDto;
import com.highload.chatic.dto.message.MessageResponseDto;
import com.highload.chatic.dto.personalchat.PersonalChatRequestDto;
import com.highload.chatic.dto.personalchat.PersonalChatResponseDto;
import com.highload.chatic.service.PersonalChatService;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
public class PersonalChatController {

    private final PersonalChatService service;

    public PersonalChatController(PersonalChatService service) {
        this.service = service;
    }

    @GetMapping(value = "/personal-chat/{id}")
    @ResponseStatus(HttpStatus.OK)
    @SneakyThrows
    public PersonalChatResponseDto getPersonalChat(
            @PathVariable UUID id,
            Principal principal
    ) {
        return service.getChat(principal.getName(), id);
    }

    @GetMapping(value = "/personal-chats", params = {"page", "size"})
    @ResponseStatus(HttpStatus.OK)
    @SneakyThrows
    public PageResponseDto<PersonalChatResponseDto> getPersonalChats(
            @RequestParam int page,
            @RequestParam int size,
            Principal principal
    ) {
        return service.getAllChats(principal.getName(), PageRequest.of(page, size));
    }

    @PostMapping("/personal-chat")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @SneakyThrows
    public PersonalChatResponseDto addPersonalChat(
            @RequestBody PersonalChatRequestDto chatDto,
            Principal principal
    ) {
        return service.addChat(principal.getName(), chatDto);
    }

    @DeleteMapping("/personal-chat/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SneakyThrows
    public void deletePersonalChat(
            @PathVariable UUID id,
            Principal principal
    ) {
        service.deleteChat(principal.getName(), id);
    }

}
