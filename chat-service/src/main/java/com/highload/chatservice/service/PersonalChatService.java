package com.highload.chatservice.service;

import com.highload.chatservice.dto.PageResponseDto;
import com.highload.chatservice.dto.personalchat.PersonalChatResponseDto;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface PersonalChatService {
    Mono<PersonalChatResponseDto> getChat(String username1, String username2);

    Mono<PageResponseDto<PersonalChatResponseDto>> getAllChats(String username, Pageable pageable);

    Mono<PersonalChatResponseDto> addChat(String username1, String username2);
}
