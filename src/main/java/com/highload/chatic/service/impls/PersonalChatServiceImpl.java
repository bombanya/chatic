package com.highload.chatic.service.impls;

import com.highload.chatic.repository.PersonalChatRepository;
import com.highload.chatic.dto.message.MessagePageResponseDto;
import com.highload.chatic.dto.message.MessageRequestDto;
import com.highload.chatic.dto.message.MessageResponseDto;
import com.highload.chatic.dto.personalchat.PersonalChatPageResponseDto;
import com.highload.chatic.dto.personalchat.PersonalChatRequestDto;
import com.highload.chatic.dto.personalchat.PersonalChatResponseDto;
import com.highload.chatic.exception.IllegalAccessException;
import com.highload.chatic.exception.ResourceNotFoundException;
import com.highload.chatic.models.AuthRoleName;
import com.highload.chatic.models.PersonalChat;
import com.highload.chatic.service.MessageService;
import com.highload.chatic.service.PersonService;
import com.highload.chatic.service.PersonalChatService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PersonalChatServiceImpl implements PersonalChatService {

    private final PersonalChatRepository personalChatRepository;
    private final PersonService personService;
    private final MessageService messageService;
    private final ModelMapper modelMapper;

    public PersonalChatServiceImpl(
            PersonalChatRepository personalChatRepository,
            PersonService personService,
            MessageService messageService,
            ModelMapper modelMapper
    ) {
        this.personalChatRepository = personalChatRepository;
        this.personService = personService;
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }

    @Override
    public PersonalChatResponseDto getChat(UserDetails userDetails, UUID chatId) throws ResourceNotFoundException {
        var personalChat = personalChatRepository.findById(chatId)
                .orElseThrow(ResourceNotFoundException::new);
        if (userCanNotEditChat(userDetails, chatId))
            throw new ResourceNotFoundException();
        return modelMapper.map(personalChat, PersonalChatResponseDto.class);
    }

    @Override
    public PersonalChatResponseDto addChat(UserDetails userDetails, PersonalChatRequestDto chatDto) {
        var personalChat = new PersonalChat(chatDto.person1Id(), chatDto.person2Id());
        personalChat = personalChatRepository.save(personalChat);
        return modelMapper.map(personalChat, PersonalChatResponseDto.class);
    }

    @Override
    public PersonalChatPageResponseDto getAllChats(UserDetails userDetails, Pageable pageable) throws ResourceNotFoundException {
        var user = personService.getPerson(userDetails.getUsername());
        var page = personalChatRepository.findAllByPerson1IdOrPerson2Id(user.id(), pageable)
                .map(it -> modelMapper.map(it, PersonalChatResponseDto.class));
        return new PersonalChatPageResponseDto(page.toList(), page.hasNext());
    }

    @Override
    public void deleteChat(UserDetails userDetails, UUID chatId) throws ResourceNotFoundException {
        if (userCanNotEditChat(userDetails, chatId))
            throw new ResourceNotFoundException();
        personalChatRepository.deleteById(chatId);
    }

    @Override
    public MessagePageResponseDto getAllMessages(UserDetails userDetails, UUID chatId, Pageable pageable) throws ResourceNotFoundException {
        return messageService.getPersonalChatMessages(userDetails, chatId, pageable);
    }

    @Override
    public MessageResponseDto addMessage(UserDetails userDetails, MessageRequestDto messageRequestDto) throws ResourceNotFoundException, IllegalAccessException {
        return messageService.addPersonalChatMessage(userDetails, messageRequestDto);
    }



    @Override
    public void deleteMessage(UserDetails userDetails, UUID chatId, UUID messageId) throws ResourceNotFoundException {
        messageService.deletePersonalChatMessage(userDetails, chatId, messageId);
    }

    @Override
    public void updateMessage(UserDetails userDetails, MessageRequestDto message) throws ResourceNotFoundException {
        messageService.updatePersonalChatMessage(userDetails, message);
    }

    @Override
    public boolean userCanNotEditChat(UserDetails userDetails, UUID chatId) throws ResourceNotFoundException {
        var person = personService.getPerson(userDetails.getUsername());
        if (person == null)
            throw new ResourceNotFoundException();
        return (personalChatRepository.isNotUserChat(chatId, person.id()) &&
                AuthRoleName.isNotSuperUser(userDetails.getAuthorities()));
    }
}
