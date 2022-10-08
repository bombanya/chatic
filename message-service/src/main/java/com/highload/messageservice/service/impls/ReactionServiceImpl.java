package com.highload.messageservice.service.impls;

import com.highload.messageservice.client.PersonFeignClient;
import com.highload.messageservice.dto.reaction.ReactionRequestDto;
import com.highload.messageservice.models.MessageOperation;
import com.highload.messageservice.models.Reaction;
import com.highload.messageservice.models.ReactionId;
import com.highload.messageservice.repository.ReactionRepository;
import com.highload.messageservice.service.MessageService;
import com.highload.messageservice.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;
    private final PersonFeignClient personService;
    private final MessageService messageService;

    @Override
    public Mono<Reaction> addReaction(String username, UUID messageId, ReactionRequestDto reactionRequestDto) {
        var person = personService.getPerson(username);
        messageService.authorizeOperationOnMessage(messageId, person.getId(), MessageOperation.READ);
        return reactionRepository.save(new Reaction(messageId, person.getId(), reactionRequestDto.getEmoji()));
    }

    @Override
    public Mono<Void> deleteReaction(String username, UUID messageId) {
        var person = personService.getPerson(username);
        return reactionRepository.deleteById(new ReactionId(messageId, person.getId()));
    }

    @Override
    public Mono<PageImpl<Reaction>> getReactions(String username, UUID messageId, Pageable pageable) {
        var person = personService.getPerson(username);
        messageService.authorizeOperationOnMessage(messageId, person.getId(), MessageOperation.READ);

        return reactionRepository.findByReactionId_MessageId(messageId, pageable)
                .collectList()
                .zipWith(this.reactionRepository.count())
                .map(t -> new PageImpl<>(t.getT1(), pageable, t.getT2()));

    }


}
