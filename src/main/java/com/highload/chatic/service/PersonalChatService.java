package com.highload.chatic.service;

import com.highload.chatic.dto.PageResponseDto;
import com.highload.chatic.dto.personalchat.PersonalChatResponseDto;
import org.springframework.data.domain.Pageable;

public interface PersonalChatService {
    PersonalChatResponseDto getChat(String username1, String username2);

    PageResponseDto<PersonalChatResponseDto> getAllChats(String username, Pageable pageable);

    PersonalChatResponseDto addChat(String username1, String username2);
}
