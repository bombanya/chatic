package com.highload.chatservice.service.impls;

import com.highload.chatservice.dto.personalchat.PersonalChatResponseDto;
import com.highload.chatservice.models.PersonalChat;
import com.highload.chatservice.repository.PersonalChatRepository;
import com.highload.chatservice.service.PersonalChatService;
import com.highload.chatservice.client.PersonFeignClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonalChatServiceImpl implements PersonalChatService {

    private final PersonalChatRepository personalChatRepository;
    private final PersonFeignClient personService;
    private final ModelMapper modelMapper;

    @Override
    public Mono<PersonalChatResponseDto> getChat(String username1, String username2) {
        var person1 = personService.getPerson(username1);
        var person2 = personService.getPerson(username2);
        /*var personalChat = personalChatRepository
                .findByPerson1IdAndPerson2Id(person1.getId(), person2.getId())
                .orElseThrow(ResourceNotFoundException::new);
        return modelMapper.map(personalChat, PersonalChatResponseDto.class);*/
        return personalChatRepository.findByPerson1IdAndPerson2Id(person1.getId(), person2.getId())
                .map(
                        ch -> modelMapper.map(ch, PersonalChatResponseDto.class)
                );
    }

    @Override
    public Mono<PersonalChat> addChat(String username1, String username2) {
        var person1 = personService.getPerson(username1);
        var person2 = personService.getPerson(username2);
        /*var personalChat = personalChatRepository
                .findByPerson1IdAndPerson2Id(person1.getId(), person2.getId())
                .orElseGet(() -> personalChatRepository
                        .save(new PersonalChat(person1.getId(), person2.getId())));*/
        return personalChatRepository.save(new PersonalChat(person1.getId(), person2.getId()));
    }

    @Override
    public Mono<PageImpl<PersonalChat>> getAllChats(String username, Pageable pageable) {
        var user = personService.getPerson(username);

        return personalChatRepository.findAllUserChats(user.getId(), pageable)
                .collectList()
                .zipWith(this.personalChatRepository.count())
                .map(t -> new PageImpl<>(t.getT1(), pageable, t.getT2()));

        /*var page = personalChatRepository
                .findAllUserChats(user.getId(), pageable)
                .map(it -> modelMapper.map(it, PersonalChatResponseDto.class));
        return new PageResponseDto<>(page);*/
    }
}
