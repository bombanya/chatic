package com.highload.chatservice.service;

import com.highload.chatservice.dto.PageResponseDto;
import com.highload.chatservice.dto.personalchat.PersonalChatResponseDto;
import org.springframework.data.domain.Pageable;

public interface PersonalChatService {
    PersonalChatResponseDto getChat(String username1, String username2);

    PageResponseDto<PersonalChatResponseDto> getAllChats(String username, Pageable pageable);

    PersonalChatResponseDto addChat(String username1, String username2);
}
