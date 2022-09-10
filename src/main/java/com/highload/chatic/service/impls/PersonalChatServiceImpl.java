package com.highload.chatic.service.impls;

import com.highload.chatic.dto.PageResponseDto;
import com.highload.chatic.dto.personalchat.PersonalChatRequestDto;
import com.highload.chatic.dto.personalchat.PersonalChatResponseDto;
import com.highload.chatic.exception.ResourceNotFoundException;
import com.highload.chatic.models.PersonalChat;
import com.highload.chatic.repository.PersonalChatRepository;
import com.highload.chatic.service.PersonService;
import com.highload.chatic.service.PersonalChatService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PersonalChatServiceImpl implements PersonalChatService {

    private final PersonalChatRepository personalChatRepository;
    private final PersonService personService;
    private final ModelMapper modelMapper;

    public PersonalChatServiceImpl(
            PersonalChatRepository personalChatRepository,
            PersonService personService,
            ModelMapper modelMapper
    ) {
        this.personalChatRepository = personalChatRepository;
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    @Override
    public PersonalChatResponseDto getChat(String username, UUID chatId) throws ResourceNotFoundException {
        var personalChat = personalChatRepository.findById(chatId)
                .orElseThrow(ResourceNotFoundException::new);
        if (userCanNotEditChat(username, chatId))
            throw new ResourceNotFoundException();
        return modelMapper.map(personalChat, PersonalChatResponseDto.class);
    }

    @Override
    public PersonalChatResponseDto addChat(String username, PersonalChatRequestDto chatDto) {
        var personalChat = new PersonalChat(chatDto.person1Id(), chatDto.person2Id());
        personalChat = personalChatRepository.save(personalChat);
        return modelMapper.map(personalChat, PersonalChatResponseDto.class);
    }

    @Override
    public PageResponseDto<PersonalChatResponseDto> getAllChats(String username, Pageable pageable) throws ResourceNotFoundException {
        var user = personService.getPerson(username);
        var page = personalChatRepository.findAllUserChats(user.id(), pageable)
                .map(it -> modelMapper.map(it, PersonalChatResponseDto.class));
        return new PageResponseDto<>(page);
    }

    @Override
    public void deleteChat(String username, UUID chatId) throws ResourceNotFoundException {
        if (userCanNotEditChat(username, chatId))
            throw new ResourceNotFoundException();
        personalChatRepository.deleteById(chatId);
    }

    @Override
    public boolean userCanNotEditChat(String username, UUID chatId) throws ResourceNotFoundException {
        var person = personService.getPerson(username);
        return personalChatRepository.isNotUserChat(chatId, person.id());
    }
}
