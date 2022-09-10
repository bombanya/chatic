package com.highload.chatic.service;

import com.highload.chatic.dto.PageResponseDto;
import com.highload.chatic.dto.personalchat.PersonalChatRequestDto;
import com.highload.chatic.dto.personalchat.PersonalChatResponseDto;
import com.highload.chatic.exception.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PersonalChatService {
    PersonalChatResponseDto getChat(String username, UUID chatId) throws ResourceNotFoundException;

    PageResponseDto<PersonalChatResponseDto> getAllChats(String username, Pageable pageable) throws ResourceNotFoundException;

    PersonalChatResponseDto addChat(String username, PersonalChatRequestDto chat);

    void deleteChat(String username, UUID chatId) throws ResourceNotFoundException;

    boolean userCanNotEditChat(String username, UUID chatId) throws ResourceNotFoundException;
}
