package com.highload.chatservice.service.impls;

import com.highload.chatservice.client.PersonFeignClient;
import com.highload.chatservice.dto.PageResponseDto;
import com.highload.chatservice.dto.personalchat.PersonalChatResponseDto;
import com.highload.chatservice.exception.ResourceNotFoundException;
import com.highload.chatservice.models.PersonalChat;
import com.highload.chatservice.repository.PersonalChatRepository;
import com.highload.chatservice.service.PersonalChatService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class PersonalChatServiceImpl implements PersonalChatService {

    private final PersonalChatRepository personalChatRepository;
    private final PersonFeignClient personClient;
    private final ModelMapper modelMapper;

    @Override
    public Mono<PersonalChatResponseDto> getChat(String username1, String username2) {
        var person1 = personClient.getPerson(username1);
        var person2 = personClient.getPerson(username2);
        return person1.zipWith(person2)
                .flatMap(tuple -> Mono.fromCallable(() ->
                                personalChatRepository.findByPerson1IdAndPerson2Id(
                                        tuple.getT1().getId(),
                                        tuple.getT2().getId()
                                ).orElseThrow(ResourceNotFoundException::new)
                        ).subscribeOn(Schedulers.boundedElastic())
                ).map(personalChat -> modelMapper.map(personalChat, PersonalChatResponseDto.class));
    }

    @Override
    public Mono<PersonalChatResponseDto> addChat(String username1, String username2) {
        var person1 = personClient.getPerson(username1);
        var person2 = personClient.getPerson(username2);
        return person1.zipWith(person2)
                .flatMap(tuple -> Mono.fromCallable(() ->
                                personalChatRepository.findByPerson1IdAndPerson2Id(
                                        tuple.getT1().getId(),
                                        tuple.getT2().getId()
                                ).orElseGet(() ->
                                        personalChatRepository.save(PersonalChat.builder()
                                                .person1Id(tuple.getT1().getId())
                                                .person2Id(tuple.getT2().getId())
                                                .build())
                                )
                        ).subscribeOn(Schedulers.boundedElastic())
                ).map(personalChat -> modelMapper.map(personalChat, PersonalChatResponseDto.class));
    }

    @Override
    public Mono<PageResponseDto<PersonalChat>> getAllChats(String username, Pageable pageable) {
        var user = personClient.getPerson(username);
        return user.flatMap(personResponseDto ->
                Mono.fromCallable(() ->
                        personalChatRepository.findAllUserChats(personResponseDto.getId(), pageable)
                ).subscribeOn(Schedulers.boundedElastic())
        ).map(PageResponseDto::new);
    }
}
