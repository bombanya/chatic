package com.highload.chatic.service.impls;

import com.highload.chatic.dto.PageResponseDto;
import com.highload.chatic.dto.reaction.ReactionRequestDto;
import com.highload.chatic.dto.reaction.ReactionResponseDto;
import com.highload.chatic.exception.ResourceNotFoundException;
import com.highload.chatic.models.MessageOperation;
import com.highload.chatic.models.Reaction;
import com.highload.chatic.models.ReactionId;
import com.highload.chatic.repository.ReactionRepository;
import com.highload.chatic.service.MessageService;
import com.highload.chatic.service.PersonService;
import com.highload.chatic.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;
    private final PersonService personService;
    private final MessageService messageService;

    @Override
    public void addReaction(String username, UUID messageId, ReactionRequestDto reactionRequestDto) {
        var person = personService.getPerson(username);
        messageService.authorizeOperationOnMessage(messageId, person.getId(), MessageOperation.READ);
        reactionRepository.save(new Reaction(messageId, person.getId(), reactionRequestDto.getEmoji()));
    }

    @Override
    public void deleteReaction(String username, UUID messageId) {
        var person = personService.getPerson(username);
        reactionRepository.findById(new ReactionId(messageId, person.getId()))
                .orElseThrow(ResourceNotFoundException::new);
        reactionRepository.deleteById(new ReactionId(messageId, person.getId()));
    }

    @Override
    public PageResponseDto<ReactionResponseDto> getReactions(String username, UUID messageId, Pageable pageable) {
        var person = personService.getPerson(username);
        messageService.authorizeOperationOnMessage(messageId, person.getId(), MessageOperation.READ);
        var page = reactionRepository.findByReactionId_MessageId(messageId, pageable)
                .map(it -> new ReactionResponseDto(it.getReactionId().getPersonId(), it.getEmoji()));
        return new PageResponseDto<>(page);
    }


}
