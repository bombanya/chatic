package com.highload.chatservice.service.impls;

import com.highload.chatservice.dto.PageResponseDto;
import com.highload.chatservice.dto.personalchat.PersonalChatResponseDto;
import com.highload.chatservice.exception.InvalidRequestException;
import com.highload.chatservice.exception.ResourceNotFoundException;
import com.highload.chatservice.models.PersonalChat;
import com.highload.chatservice.repository.PersonalChatRepository;
import com.highload.chatservice.service.PersonService;
import com.highload.chatservice.service.PersonalChatService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonalChatServiceImpl implements PersonalChatService {

    private final PersonalChatRepository personalChatRepository;
    private final PersonService personService;
    private final ModelMapper modelMapper;

    @Override
    public PersonalChatResponseDto getChat(String username1, String username2) {
        var person1 = personService.getPerson(username1);
        var person2 = personService.getPerson(username2);
        var personalChat = personalChatRepository
                .findByPerson1IdAndPerson2Id(person1.getId(), person2.getId())
                .orElseThrow(ResourceNotFoundException::new);
        return modelMapper.map(personalChat, PersonalChatResponseDto.class);
    }

    @Override
    public PersonalChatResponseDto addChat(String username1, String username2) {
        var person1 = personService.getPerson(username1);
        var person2 = personService.getPerson(username2);
        if (personalChatRepository
                .findByPerson1IdAndPerson2Id(person1.getId(), person2.getId()).isPresent())
            throw new InvalidRequestException();
        var personalChat = personalChatRepository
                .findByPerson1IdAndPerson2Id(person1.getId(), person2.getId())
                .orElseGet(() -> personalChatRepository
                        .save(new PersonalChat(person1.getId(), person2.getId())));
        return modelMapper.map(personalChat, PersonalChatResponseDto.class);
    }

    @Override
    public PageResponseDto<PersonalChatResponseDto> getAllChats(String username, Pageable pageable) {
        var user = personService.getPerson(username);
        var page = personalChatRepository
                .findAllUserChats(user.getId(), pageable)
                .map(it -> modelMapper.map(it, PersonalChatResponseDto.class));
        return new PageResponseDto<>(page);
    }
}
