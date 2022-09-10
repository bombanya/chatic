package com.highload.chatic.service.impls;

import com.highload.chatic.dto.PageResponseDto;
import com.highload.chatic.dto.ReactionDto;
import com.highload.chatic.dto.personalchat.PersonalChatResponseDto;
import com.highload.chatic.exception.IllegalAccessException;
import com.highload.chatic.exception.ResourceNotFoundException;
import com.highload.chatic.models.Emoji;
import com.highload.chatic.models.Person;
import com.highload.chatic.models.Reaction;
import com.highload.chatic.models.ReactionId;
import com.highload.chatic.repository.MessageContentRepository;
import com.highload.chatic.repository.MessageRepository;
import com.highload.chatic.repository.PersonRepository;
import com.highload.chatic.repository.ReactionRepository;
import com.highload.chatic.service.PersonalChatService;
import com.highload.chatic.service.ReactionService;
import liquibase.repackaged.org.apache.commons.lang3.EnumUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;
    private final PersonRepository personRepository;
    private final PersonalChatService personalChatService;
    private final ModelMapper modelMapper;

    public ReactionServiceImpl(
            ReactionRepository reactionRepository,
            PersonRepository personRepository,
            PersonalChatService personalChatService,
            ModelMapper modelMapper
    ) {
        this.reactionRepository = reactionRepository;
        this.personRepository = personRepository;
        this.personalChatService = personalChatService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addReaction(String username, UUID id, ReactionDto reactionDto) throws ResourceNotFoundException {
        //if (personalChatService.userCanNotEditChat(username, id))
        //    throw new ResourceNotFoundException();
        if (!EnumUtils.isValidEnum(Emoji.class, reactionDto.getEmoji())) throw new ResourceNotFoundException();
        var reaction = modelMapper.map(reactionDto, Reaction.class);
        var person = personRepository.findByUsername(username);
        reaction.setReactionId(new ReactionId(id, person.get().getId()));
        reactionRepository.save(reaction);
    }

    @Override
    public void deleteReaction(String username, UUID id) throws ResourceNotFoundException {
        var person = personRepository.findByUsername(username);

        reactionRepository.deleteById(new ReactionId(id, person.get().getId()));
    }

    @Override
    public PageResponseDto<ReactionDto> getReactions(String username, UUID id, Pageable pageable) throws ResourceNotFoundException {
        var page = reactionRepository.findReactions(id, pageable)
                .map(it -> modelMapper.map(it, ReactionDto.class));
        return new PageResponseDto<>(page);
    }


}
