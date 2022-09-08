package com.highload.chatic.service;

import com.highload.chatic.dto.message.MessagePageResponseDto;
import com.highload.chatic.dto.message.MessageRequestDto;
import com.highload.chatic.dto.message.MessageResponseDto;
import com.highload.chatic.exception.IllegalAccessException;
import com.highload.chatic.exception.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface MessageService {

    void updatePersonalChatMessage(UserDetails userDetails, MessageRequestDto messageRequestDto) throws ResourceNotFoundException;

    void deletePersonalChatMessage(UserDetails userDetails, UUID chatId, UUID messageId) throws ResourceNotFoundException;

    MessageResponseDto addPersonalChatMessage(UserDetails userDetails, MessageRequestDto messageRequestDto) throws IllegalAccessException, ResourceNotFoundException;

    MessagePageResponseDto getPersonalChatMessages(UserDetails userDetails, UUID chatId, Pageable pageable) throws ResourceNotFoundException;
}
