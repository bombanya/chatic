package com.highload.chatic.service;

import com.highload.chatic.dto.message.MessagePageResponseDto;
import com.highload.chatic.dto.message.MessageRequestDto;
import com.highload.chatic.dto.message.MessageResponseDto;
import com.highload.chatic.dto.personalchat.PersonalChatPageResponseDto;
import com.highload.chatic.dto.personalchat.PersonalChatRequestDto;
import com.highload.chatic.dto.personalchat.PersonalChatResponseDto;
import com.highload.chatic.exception.IllegalAccessException;
import com.highload.chatic.exception.InvalidRequestException;
import com.highload.chatic.exception.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface PersonalChatService {
    PersonalChatResponseDto getChat(UserDetails userDetails, UUID chatId) throws ResourceNotFoundException;

    PersonalChatPageResponseDto getAllChats(UserDetails userDetails, Pageable pageable) throws ResourceNotFoundException;

    PersonalChatResponseDto addChat(UserDetails userDetails, PersonalChatRequestDto chat);

    void deleteChat(UserDetails userDetails, UUID chatId) throws ResourceNotFoundException;

    MessagePageResponseDto getAllMessages(UserDetails userDetails, UUID chatId, Pageable pageable) throws ResourceNotFoundException;

    MessageResponseDto addMessage(UserDetails userDetails, MessageRequestDto message) throws InvalidRequestException, ResourceNotFoundException, IllegalAccessException;

    void updateMessage(UserDetails userDetails, MessageRequestDto message) throws ResourceNotFoundException;

    void deleteMessage(UserDetails userDetails, UUID chatId, UUID messageId) throws ResourceNotFoundException;

    boolean userCanNotEditChat(UserDetails userDetails, UUID chatId) throws ResourceNotFoundException;
}
