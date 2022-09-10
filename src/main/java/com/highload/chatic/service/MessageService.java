package com.highload.chatic.service;

import com.highload.chatic.dto.PageResponseDto;
import com.highload.chatic.dto.message.MessageRequestDto;
import com.highload.chatic.dto.message.MessageResponseDto;
import com.highload.chatic.exception.IllegalAccessException;
import com.highload.chatic.exception.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface MessageService {

    void updatePersonalChatMessage(String username, UUID chatId, MessageRequestDto messageRequestDto) throws ResourceNotFoundException;

    void deletePersonalChatMessage(String username, UUID chatId, UUID messageId) throws ResourceNotFoundException;

    MessageResponseDto addPersonalChatMessage(String username, UUID chatId, MessageRequestDto messageRequestDto) throws IllegalAccessException, ResourceNotFoundException;

    PageResponseDto<MessageResponseDto> getPersonalChatMessages(String username, UUID chatId, Pageable pageable) throws ResourceNotFoundException;
}
