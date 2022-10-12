package com.highload.messageservice.service.impls;

import com.highload.messageservice.client.PersonFeignClient;
import com.highload.messageservice.dto.reaction.ReactionRequestDto;
import com.highload.messageservice.dto.reaction.ReactionResponseDto;
import com.highload.messageservice.models.MessageOperation;
import com.highload.messageservice.models.Reaction;
import com.highload.messageservice.repository.ReactionRepository;
import com.highload.messageservice.service.MessageService;
import com.highload.messageservice.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;
    private final PersonFeignClient personClient;
    private final MessageService messageService;
    private final ModelMapper modelMapper;

    @Override
    public Mono<?> addReaction(String username, UUID messageId, ReactionRequestDto reactionRequestDto) {
        var person = personClient.getPerson(username);
        return person.flatMap(personResponseDto ->
                messageService.authorizeOperationOnMessage(messageId,
                        personResponseDto.getId(), MessageOperation.READ))
                .flatMap(x -> person)
                .flatMap(personResponseDto ->
                        reactionRepository.findByMessageIdAndPersonId(messageId, personResponseDto.getId())
                                .switchIfEmpty(Mono.just(Reaction.builder()
                                        .id(UUID.randomUUID())
                                        .messageId(messageId)
                                        .personId(personResponseDto.getId())
                                        .isNew(true)
                                        .build())))
                .map(reaction -> {
                    reaction.setEmoji(reactionRequestDto.getEmoji());
                    return reaction;
                })
                .flatMap(reactionRepository::save);
    }

    @Override
    public Mono<?> deleteReaction(String username, UUID messageId) {
        return personClient.getPerson(username)
                .flatMap(personResponseDto ->
                        reactionRepository.findByMessageIdAndPersonId(messageId, personResponseDto.getId()));
    }

    @Override
    public Mono<PageImpl<ReactionResponseDto>> getReactions(String username, UUID messageId, Pageable pageable) {
        return personClient.getPerson(username)
                .flatMap(personResponseDto ->
                        messageService.authorizeOperationOnMessage(messageId,
                                personResponseDto.getId(), MessageOperation.READ))
                .flatMapMany(x -> reactionRepository.findByMessageId(messageId, pageable))
                .map(reaction -> modelMapper.map(reaction, ReactionResponseDto.class))
                .collectList()
                .zipWith(reactionRepository.countByMessageId(messageId))
                .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()));
    }
}
