package com.highload.chatic.service.impls;

import com.highload.chatic.dto.PageResponseDto;
import com.highload.chatic.dto.message.MessageRequestDto;
import com.highload.chatic.dto.message.MessageResponseDto;
import com.highload.chatic.exception.IllegalAccessException;
import com.highload.chatic.exception.ResourceNotFoundException;
import com.highload.chatic.models.Message;
import com.highload.chatic.models.MessageContent;
import com.highload.chatic.repository.MessageContentRepository;
import com.highload.chatic.repository.MessageRepository;
import com.highload.chatic.service.MessageService;
import com.highload.chatic.service.PersonService;
import com.highload.chatic.service.PersonalChatService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageContentRepository messageContentRepository;
    private final PersonalChatService personalChatService;
    private final PersonService personService;

    public MessageServiceImpl(
            MessageRepository messageRepository,
            MessageContentRepository messageContentRepository,
            PersonalChatService personalChatService,
            PersonService personService
    ) {
        this.messageRepository = messageRepository;
        this.messageContentRepository = messageContentRepository;
        this.personalChatService = personalChatService;
        this.personService = personService;
    }

    @Override
    public PageResponseDto<MessageResponseDto> getPersonalChatMessages(String username, UUID chatId, Pageable pageable) throws ResourceNotFoundException {
        if (personalChatService.userCanNotEditChat(username, chatId))
            throw new ResourceNotFoundException();
        var messagesPage = messageRepository.findAllByChatIdOrderByTimestampDesc(chatId, pageable);
        var messages = messagesPage.stream().collect(Collectors.toMap(
                Message::getId, Function.identity()
        ));
        var contents =
                messageContentRepository.findAllById(messages.values().stream().map(Message::getId).toList());
        return new PageResponseDto<>(
                MessageResponseDto.fromMessagesWithContent(messages, contents),
                messagesPage.hasNext(),
                pageable
        );
    }

    @Override
    public void updatePersonalChatMessage(String username, UUID chatId, MessageRequestDto messageRequestDto) throws ResourceNotFoundException {
        if (personalChatService.userCanNotEditChat(username, chatId))
            throw new ResourceNotFoundException();
        var content = messageContentRepository.findByMessageId(messageRequestDto.id())
                .orElseThrow(ResourceNotFoundException::new);
        content.setText(messageRequestDto.textContent());
        messageContentRepository.save(content);
    }

    @Override
    public void deletePersonalChatMessage(String username, UUID chatId, UUID messageId) throws ResourceNotFoundException {
        if (personalChatService.userCanNotEditChat(username, chatId))
            throw new ResourceNotFoundException();
        messageRepository.deleteById(messageId);
    }

    @Override
    @Transactional
    public MessageResponseDto addPersonalChatMessage(String username, UUID chatId, MessageRequestDto messageRequestDto) throws IllegalAccessException, ResourceNotFoundException {
        if (personalChatService.userCanNotEditChat(username, chatId))
            throw new IllegalAccessException();
        var author = personService.getPerson(username);
        var message = new Message(chatId, author.id(), messageRequestDto.replyId());
        message = messageRepository.save(message);
        var content = new MessageContent(message.getId(), messageRequestDto.textContent());
        content = messageContentRepository.save(content);
        return MessageResponseDto.fromMessageWithContent(message, content);
    }
}
