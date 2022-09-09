package com.highload.chatic.service.impls;

import com.highload.chatic.repository.MessageContentRepository;
import com.highload.chatic.repository.MessageRepository;
import com.highload.chatic.dto.message.MessagePageResponseDto;
import com.highload.chatic.dto.message.MessageRequestDto;
import com.highload.chatic.dto.message.MessageResponseDto;
import com.highload.chatic.exception.IllegalAccessException;
import com.highload.chatic.exception.ResourceNotFoundException;
import com.highload.chatic.models.Message;
import com.highload.chatic.models.MessageContent;
import com.highload.chatic.service.MessageService;
import com.highload.chatic.service.PersonalChatService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
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

    public MessageServiceImpl(
            MessageRepository messageRepository,
            MessageContentRepository messageContentRepository,
            @Lazy PersonalChatService personalChatService
    ) {
        this.messageRepository = messageRepository;
        this.messageContentRepository = messageContentRepository;
        this.personalChatService = personalChatService;
    }

    @Override
    public MessagePageResponseDto getPersonalChatMessages(UserDetails userDetails, UUID chatId, Pageable pageable) throws ResourceNotFoundException {
        if (personalChatService.userCanNotEditChat(userDetails, chatId))
            throw new ResourceNotFoundException();
        var messagesPage = messageRepository.findAllByChatIdOrderByTimestampDesc(chatId, pageable);
        var messages = messagesPage.stream().collect(Collectors.toMap(
                Message::getId, Function.identity()
        ));
        var contents =
                messageContentRepository.findAllById(messages.values().stream().map(Message::getId).toList());
        return MessagePageResponseDto.fromMessage(messages, contents, messagesPage.hasNext());
    }

    @Override
    public void updatePersonalChatMessage(UserDetails userDetails, MessageRequestDto messageRequestDto) throws ResourceNotFoundException {
        if (personalChatService.userCanNotEditChat(userDetails, messageRequestDto.chatId()))
            throw new ResourceNotFoundException();
        var content = messageContentRepository.findByMessageId(messageRequestDto.id())
                .orElseThrow(ResourceNotFoundException::new);
        content.setText(messageRequestDto.textContent());
        messageContentRepository.save(content);
    }

    @Override
    public void deletePersonalChatMessage(UserDetails userDetails, UUID chatId, UUID messageId) throws ResourceNotFoundException {
        if (personalChatService.userCanNotEditChat(userDetails, chatId))
            throw new ResourceNotFoundException();
        messageRepository.deleteById(messageId);
    }

    @Override
    @Transactional
    public MessageResponseDto addPersonalChatMessage(UserDetails userDetails, MessageRequestDto messageRequestDto) throws IllegalAccessException, ResourceNotFoundException {
        if (personalChatService.userCanNotEditChat(userDetails, messageRequestDto.chatId()))
            throw new IllegalAccessException();
        var message = new Message(messageRequestDto.chatId(), messageRequestDto.authorId(), messageRequestDto.replyId());
        message = messageRepository.save(message);
        var content = new MessageContent(message.getId(), messageRequestDto.textContent());
        content = messageContentRepository.save(content);
        return MessageResponseDto.fromMessage(message, content);
    }
}
