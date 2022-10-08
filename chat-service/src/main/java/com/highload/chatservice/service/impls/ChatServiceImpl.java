package com.highload.chatservice.service.impls;

import com.highload.chatservice.client.PersonFeignClient;
import com.highload.chatservice.client.shared.PersonResponseDto;
import com.highload.chatservice.dto.GroupMemberDto;
import com.highload.chatservice.exception.IllegalAccessException;
import com.highload.chatservice.exception.InvalidRequestException;
import com.highload.chatservice.exception.ResourceNotFoundException;
import com.highload.chatservice.models.MessageOperation;
import com.highload.chatservice.models.PGroup;
import com.highload.chatservice.models.PersonalChat;
import com.highload.chatservice.repository.ChatRepository;
import com.highload.chatservice.service.ChatService;
import com.highload.chatservice.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    public Mono<?> authorizeOperation(UUID chatId, UUID personId, MessageOperation operation) {
        return Mono.fromCallable(() -> chatRepository.findById(chatId)
                        .orElseThrow(ResourceNotFoundException::new))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(chat -> {
                    if (chat instanceof PersonalChat)
                        return authorizeInPersonalChat(personId, (PersonalChat) chat, operation);
                    else if (chat instanceof PGroup)
                        return authorizeInGroup(groupService.getGroupMemberInfo(chatId, personId), operation);
                    else return Mono.error(new IllegalAccessException());
                });
    }

    private Mono<?> authorizeInPersonalChat(UUID personId, PersonalChat personalChat, MessageOperation operation) {
        Mono<PersonResponseDto> person1 = personFeignClient.getPerson(personalChat.getPerson1Id())
                .filter(person -> !person.isDeleted())
                .switchIfEmpty(Mono.error(new InvalidRequestException()));
        Mono<PersonResponseDto> person2 = personFeignClient.getPerson(personalChat.getPerson2Id())
                .filter(person -> !person.isDeleted())
                .switchIfEmpty(Mono.error(new InvalidRequestException()));
        if (operation == MessageOperation.READ) return Mono.empty();
        else return person1.zipWith(person2)
                .filter(tuple -> personalChat.getPerson1Id().equals(personId) ||
                        personalChat.getPerson2Id().equals(personId))
                .switchIfEmpty(Mono.error(new IllegalAccessException()));
    }

    private Mono<?> authorizeInGroup(Mono<GroupMemberDto> groupMemberDto, MessageOperation operation) {
        return groupMemberDto
                .map(groupMemberInfo -> {
                    if (operation == MessageOperation.READ) return groupMemberInfo;
                    else if (operation == MessageOperation.WRITE &&
                            groupMemberInfo.getRole().isWritePosts()) return groupMemberInfo;
                    else if (operation == MessageOperation.REPLY &&
                            groupMemberInfo.getRole().isWriteComments()) return groupMemberInfo;
                    throw new IllegalAccessException();
                });
    }
}
