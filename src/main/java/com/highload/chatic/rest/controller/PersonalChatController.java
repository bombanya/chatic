package com.highload.chatic.rest.controller;

import com.highload.chatic.dto.message.MessagePageResponseDto;
import com.highload.chatic.dto.personalchat.PersonalChatPageResponseDto;
import com.highload.chatic.dto.personalchat.PersonalChatRequestDto;
import com.highload.chatic.dto.personalchat.PersonalChatResponseDto;
import com.highload.chatic.exception.ResourceNotFoundException;
import com.highload.chatic.service.PersonalChatService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
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
    public PersonalChatResponseDto getPersonalChat(
            @PathVariable UUID id,
            Principal principal
    ) throws ResourceNotFoundException {
        return service.getChat((UserDetails) principal, id);
    }

    @GetMapping(value = "/personal-chats", params = {"page", "size"})
    @ResponseStatus(HttpStatus.OK)
    public PersonalChatPageResponseDto getPersonalChats(
            @RequestParam int page,
            @RequestParam int size,
            Principal principal
    ) throws ResourceNotFoundException {
        return service.getAllChats((UserDetails) principal, PageRequest.of(page, size));
    }

    @PostMapping("/personal-chat")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public PersonalChatResponseDto addPersonalChat(
            @RequestBody PersonalChatRequestDto chatDto,
            Principal principal
    ) {
        return service.addChat((UserDetails) principal, chatDto);
    }

    @DeleteMapping("/personal-chat/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePersonalChat(
            @PathVariable UUID id,
            Principal principal
    ) throws ResourceNotFoundException {
        service.deleteChat((UserDetails) principal, id);
    }

    @GetMapping(value = "/personal-chat/{id}/messages", params = {"page", "size"})
    @ResponseStatus(HttpStatus.OK)
    public MessagePageResponseDto getMessages(
            @PathVariable UUID id,
            @RequestParam int page,
            @RequestParam int size,
            Principal principal
    ) throws ResourceNotFoundException {
        return service.getAllMessages((UserDetails) principal, id, PageRequest.of(page, size));
    }

}
