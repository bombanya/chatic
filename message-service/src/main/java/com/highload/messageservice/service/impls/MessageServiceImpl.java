package com.highload.messageservice.service.impls;

import com.highload.messageservice.client.ChatFeignClient;
import com.highload.messageservice.client.PersonFeignClient;
import com.highload.messageservice.dto.PageResponseDto;
import com.highload.messageservice.dto.message.MessageRequestDto;
import com.highload.messageservice.dto.message.MessageResponseDto;
import com.highload.messageservice.exception.IllegalAccessException;
import com.highload.messageservice.exception.InvalidRequestException;
import com.highload.messageservice.exception.ResourceNotFoundException;
import com.highload.messageservice.models.Message;
import com.highload.messageservice.models.MessageContent;
import com.highload.messageservice.models.MessageOperation;
import com.highload.messageservice.repository.MessageContentRepository;
import com.highload.messageservice.repository.MessageRepository;
import com.highload.messageservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageContentRepository messageContentRepository;
    private final PersonFeignClient personService;
    private final ChatFeignClient chatService;

    @Override
    @Transactional
    public void updateMessage(String username, UUID messageID, MessageRequestDto messageRequestDto) {
        var person = personService.getPerson(username);
        checkPersonIsAuthor(person.getId(), messageID);
        messageContentRepository.updateMessageContent(messageID, messageRequestDto.textContent());
    }

    @Override
    public void deleteMessage(String username, UUID messageId) {
        var person = personService.getPerson(username);
        checkPersonIsAuthor(person.getId(), messageId);
        messageRepository.deleteById(messageId);
    }

    @Override
    @Transactional
    public void addMessage(String username, MessageRequestDto messageRequestDto) {
        var person = personService.getPerson(username);
        chatService.authorizeOperation(messageRequestDto.chatId(), person.getId(), MessageOperation.WRITE);
        Message message = new Message(messageRequestDto.chatId(), person.getId(), null);
        messageRepository.save(message);
        messageContentRepository.save(new MessageContent(message.getId(), messageRequestDto.textContent()));
    }

    @Override
    @Transactional
    public void addReply(String username, UUID replyId, MessageRequestDto messageRequestDto) {
        var person = personService.getPerson(username);
        chatService.authorizeOperation(messageRequestDto.chatId(), person.getId(), MessageOperation.REPLY);
        messageRepository.findById(replyId).orElseThrow(InvalidRequestException::new);
        Message message = new Message(messageRequestDto.chatId(), person.getId(), replyId);
        messageRepository.save(message);
        messageContentRepository.save(new MessageContent(message.getId(), messageRequestDto.textContent()));
    }

    @Override
    public MessageResponseDto getMessage(String username, UUID messageId) {
        var person = personService.getPerson(username);
        var message = messageRepository.findById(messageId).orElseThrow(ResourceNotFoundException::new);
        chatService.authorizeOperation(message.getChatId(), person.getId(), MessageOperation.READ);
        var content = messageContentRepository.findByMessageId(messageId)
                .orElseThrow(ResourceNotFoundException::new);
        return MessageResponseDto.fromMessageWithContent(message, content);
    }

    @Override
    public PageResponseDto<MessageResponseDto> getChatMessages(String username, UUID chatId, Pageable pageable) {
        var person = personService.getPerson(username);
        chatService.authorizeOperation(chatId, person.getId(), MessageOperation.READ);
        var messages = messageRepository.findAllByChatIdOrderByTimestampDesc(chatId, pageable);
        var contents = messageContentRepository.findByMessageIdIn(messages
                .getContent()
                .stream()
                .map(Message::getId)
                .toList());
        return new PageResponseDto<>(MessageResponseDto.fromMessagesWithContent(messages.getContent(), contents),
                messages);
    }

    @Override
    public PageResponseDto<MessageResponseDto> getMessageReplies(String username, UUID messageId, Pageable pageable) {
        var person = personService.getPerson(username);
        var message = messageRepository.findById(messageId).orElseThrow(ResourceNotFoundException::new);
        chatService.authorizeOperation(message.getChatId(), person.getId(), MessageOperation.READ);
        var messages = messageRepository.findAllByReplyIdOrderByTimestampDesc(messageId, pageable);
        var contents = messageContentRepository.findByMessageIdIn(messages
                .getContent()
                .stream()
                .map(Message::getId)
                .toList());
        return new PageResponseDto<>(MessageResponseDto.fromMessagesWithContent(messages.getContent(), contents),
                messages);
    }

    @Override
    public void authorizeOperationOnMessage(UUID messageId, UUID personId, MessageOperation operation) {
        var message = messageRepository.findById(messageId)
                .orElseThrow(ResourceNotFoundException::new);
        if (operation == MessageOperation.WRITE) throw new IllegalAccessException();
        chatService.authorizeOperation(message.getChatId(), personId, operation);
    }

    private void checkPersonIsAuthor(UUID personId, UUID messageId) {
        var message = messageRepository.findById(messageId)
                .orElseThrow(ResourceNotFoundException::new);
        if (!message.getAuthorId().equals(personId)) throw new ResourceNotFoundException();
    }
}