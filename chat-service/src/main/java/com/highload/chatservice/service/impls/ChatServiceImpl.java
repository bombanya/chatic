package com.highload.chatservice.service.impls;

import com.highload.chatservice.client.PersonFeignClient;
import com.highload.chatservice.client.shared.PersonResponseDto;
import com.highload.chatservice.exception.IllegalAccessException;
import com.highload.chatservice.exception.InvalidRequestException;
import com.highload.chatservice.exception.ResourceNotFoundException;
import com.highload.chatservice.models.ChatOperation;
import com.highload.chatservice.models.PGroup;
import com.highload.chatservice.models.PersonalChat;
import com.highload.chatservice.repository.ChatRepository;
import com.highload.chatservice.service.ChatService;
import com.highload.chatservice.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final GroupService groupService;
    private final PersonFeignClient personFeignClient;

    @Override
    public Mono<Void> authorizeOperation(UUID chatId, UUID personId, ChatOperation operation) {
        return Mono.fromCallable(() ->
                        chatRepository.findById(chatId)
                                .orElseThrow(ResourceNotFoundException::new))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(chat -> {
                    if (chat instanceof PersonalChat personalChat)
                        return authorizeInPersonalChat(personId, personalChat, operation);
                    else if (chat instanceof PGroup)
                        return groupService.authorizeOperation(chatId, personId, operation);
                    else return Mono.error(new IllegalAccessException());
                });
    }

    public Mono<Void> authorizeOperation(UUID chatId, String username, ChatOperation operation) {
        return personFeignClient.getPerson(username)
                .flatMap(person ->
                        this.authorizeOperation(chatId, person.getId(), operation)
                );
    }

    private Mono<Void> authorizeInPersonalChat(UUID personId, PersonalChat personalChat, ChatOperation operation) {
        Mono<PersonResponseDto> person1 = personFeignClient.getPerson(personalChat.getPerson1Id());
        Mono<PersonResponseDto> person2 = personFeignClient.getPerson(personalChat.getPerson2Id());

        return Flux.concat(person1, person2)
                .filter(person -> !person.isDeleted() || operation == ChatOperation.READ)
                .switchIfEmpty(Mono.error(InvalidRequestException::new))
                .filter(person -> personId.equals(person.getId()))
                .switchIfEmpty(Mono.error(IllegalAccessException::new))
                .then();
    }
}
