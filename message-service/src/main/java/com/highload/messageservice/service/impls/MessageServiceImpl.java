package com.highload.messageservice.service.impls;

import com.highload.messageservice.client.ChatFeignClient;
import com.highload.messageservice.client.PersonFeignClient;
import com.highload.messageservice.dto.message.MessageRequestDto;
import com.highload.messageservice.dto.message.MessageResponseDto;
import com.highload.messageservice.exception.IllegalAccessException;
import com.highload.messageservice.exception.ResourceNotFoundException;
import com.highload.messageservice.models.Message;
import com.highload.messageservice.models.MessageContent;
import com.highload.messageservice.models.MessageOperation;
import com.highload.messageservice.repository.MessageContentRepository;
import com.highload.messageservice.repository.MessageRepository;
import com.highload.messageservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageContentRepository messageContentRepository;
    private final PersonFeignClient personClient;
    private final ChatFeignClient chatClient;

    @Override
    @Transactional
    public Mono<?> updateMessage(String username, UUID messageID, MessageRequestDto messageRequestDto) {
        var person = personClient.getPerson(username);
        return person.flatMap(personResponseDto ->
                        checkPersonIsAuthor(personResponseDto.getId(), messageID))
                .flatMap(x -> person)
                .flatMap(personResponseDto ->
                        messageContentRepository.updateMessageContent(messageID,
                                messageRequestDto.textContent()));
    }

    @Override
    public Mono<?> deleteMessage(String username, UUID messageId) {
        return personClient.getPerson(username)
                .flatMap(personResponseDto -> checkPersonIsAuthor(personResponseDto.getId(), messageId))
                .flatMap(x -> messageRepository.deleteById(messageId));
    }

    @Override
    @Transactional
    public Mono<?> addMessage(String username, MessageRequestDto messageRequestDto) {
        var person = personClient.getPerson(username);
        return person.flatMap(personResponseDto -> chatClient
                .authorizeOperation(messageRequestDto.chatId(),
                        personResponseDto.getId(), MessageOperation.WRITE))
                .flatMap(x -> person)
                .map(personResponseDto -> Message.builder()
                        .id(UUID.randomUUID())
                        .chatId(messageRequestDto.chatId())
                        .authorId(personResponseDto.getId())
                        .build())
                .flatMap(messageRepository::save)
                .map(message -> MessageContent
                        .builder()
                        .id(UUID.randomUUID())
                        .messageId(message.getId())
                        .text(messageRequestDto.textContent())
                        .build())
                .flatMap(messageContentRepository::save);
    }

    @Override
    @Transactional
    public Mono<?> addReply(String username, UUID replyId, MessageRequestDto messageRequestDto) {
        var person = personClient.getPerson(username);
        return person.flatMap(personResponseDto ->
                chatClient.authorizeOperation(messageRequestDto.chatId(),
                        personResponseDto.getId(), MessageOperation.REPLY))
                .flatMap(x -> person)
                .zipWith(messageRepository.findById(replyId)
                        .switchIfEmpty(Mono.error(new ResourceNotFoundException())))
                .map(tuple -> Message.builder()
                        .id(UUID.randomUUID())
                        .chatId(messageRequestDto.chatId())
                        .authorId(tuple.getT1().getId())
                        .replyId(tuple.getT2().getId())
                        .build())
                .flatMap(messageRepository::save)
                .map(message -> MessageContent.builder()
                        .id(UUID.randomUUID())
                        .messageId(message.getId())
                        .text(messageRequestDto.textContent())
                        .build())
                .flatMap(messageContentRepository::save);
    }

    @Override
    public Mono<MessageResponseDto> getMessage(String username, UUID messageId) {
        var person = personClient.getPerson(username);
        var message = messageRepository.findById(messageId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException()));
        return person.zipWith(message)
                .flatMap(tuple -> chatClient.authorizeOperation(tuple.getT2().getChatId(),
                        tuple.getT1().getId(), MessageOperation.READ))
                .flatMap(x -> message)
                .zipWith(messageContentRepository.findByMessageId(messageId)
                        .switchIfEmpty(Mono.error(new ResourceNotFoundException())))
                .map(tuple -> MessageResponseDto.fromMessageWithContent(tuple.getT1(), tuple.getT2()));

    }

    @Override
    public Mono<PageImpl<MessageResponseDto>> getChatMessages(String username, UUID chatId, Pageable pageable) {
        var messages = personClient.getPerson(username)
                .flatMap(personResponseDto ->
                        chatClient.authorizeOperation(chatId, personResponseDto.getId(), MessageOperation.READ))
                .flatMapMany(x -> messageRepository.findAllByChatIdOrderByTimestampDesc(chatId, pageable))
                .collectList();
        var content = messages.flatMapMany(messageList ->
                messageContentRepository.findByMessageIdIn(messageList
                        .stream()
                        .map(Message::getId)
                        .toList()))
                .collectList();
        return messages.zipWith(content)
                .map(tuple -> MessageResponseDto.fromMessagesWithContent(tuple.getT1(), tuple.getT2()))
                .zipWith(messageRepository.countByChatId(chatId)
                        .switchIfEmpty(Mono.error(new ResourceNotFoundException())))
                .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()));
    }

    @Override
    public Mono<PageImpl<MessageResponseDto>> getMessageReplies(String username, UUID messageId, Pageable pageable) {
        var person = personClient.getPerson(username);
        var message = messageRepository.findById(messageId);
        var replies = person.zipWith(message)
                .flatMap(tuple ->
                        chatClient.authorizeOperation(tuple.getT2().getChatId(),
                                tuple.getT1().getId(), MessageOperation.READ))
                .flatMap(x -> message)
                .flatMapMany(m -> messageRepository.findAllByReplyIdOrderByTimestampDesc(m.getId(), pageable))
                .collectList();
        var content = replies.flatMapMany(messageList ->
                        messageContentRepository.findByMessageIdIn(messageList
                                .stream()
                                .map(Message::getId)
                                .toList()))
                .collectList();
        return replies.zipWith(content)
                .map(tuple -> MessageResponseDto.fromMessagesWithContent(tuple.getT1(), tuple.getT2()))
                .zipWith(messageRepository.countByReplyId(messageId)
                        .switchIfEmpty(Mono.error(new ResourceNotFoundException())))
                .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()));
    }

    @Override
    public Mono<?> authorizeOperationOnMessage(UUID messageId, UUID personId, MessageOperation operation) {
        return messageRepository.findById(messageId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException()))
                .map(m -> {
                    if (operation == MessageOperation.WRITE) throw new IllegalAccessException();
                    else return chatClient.authorizeOperation(m.getChatId(), personId, operation);
                });
    }

    private Mono<?> checkPersonIsAuthor(UUID personId, UUID messageId) {
        return messageRepository.findById(messageId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException()))
                .filter(message -> !message.getAuthorId().equals(personId))
                .switchIfEmpty(Mono.error(new IllegalAccessException()));
    }
}
