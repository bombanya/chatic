package com.highload.messageservice.service.impls;

import com.highload.messageservice.client.ChatFeignClient;
import com.highload.messageservice.client.PersonFeignClient;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
    public Mono<MessageContent> updateMessage(String username, UUID messageID, MessageRequestDto messageRequestDto) {
        personService.getPerson(username)
                        .subscribe(personResponseDto -> checkPersonIsAuthor(personResponseDto.getId(), messageID));
        return messageContentRepository.findById(messageID)
                .flatMap(s -> {
                    s.setText(messageRequestDto.textContent());
                    return messageContentRepository.save(s);
                });
    }

    @Override
    public Mono<Void> deleteMessage(String username, UUID messageId) {
        personService.getPerson(username)
                .subscribe(personResponseDto -> checkPersonIsAuthor(personResponseDto.getId(), messageId));
        return messageRepository.deleteById(messageId);
    }

    @Override
    @Transactional
    public Mono<Void> addMessage(String username, MessageRequestDto messageRequestDto) {
        var person = personService.getPerson(username).block();
        chatService.authorizeOperation(messageRequestDto.chatId(), person.getId(), MessageOperation.WRITE);
        Message message = new Message(messageRequestDto.chatId(), person.getId(), null);
        return messageRepository.save(message)
                .and(
                        messageContentRepository.save(new MessageContent(message.getId(), messageRequestDto.textContent()))
                );
    }

    @Override
    @Transactional
    public Mono<Void> addReply(String username, UUID replyId, MessageRequestDto messageRequestDto) {
        var person = personService.getPerson(username).block();
        chatService.authorizeOperation(messageRequestDto.chatId(), person.getId(), MessageOperation.REPLY);
        messageRepository.findById(replyId).switchIfEmpty(Mono.error(InvalidRequestException::new));//todo
        Message message = new Message(messageRequestDto.chatId(), person.getId(), replyId);
        return messageRepository.save(message)
                .and(
                        messageContentRepository.save(new MessageContent(message.getId(), messageRequestDto.textContent()))
                );
    }

    @Override
    public Mono<MessageResponseDto> getMessage(String username, UUID messageId) {
        personService.getPerson(username)
                .zipWith(messageRepository.findById(messageId))
                .subscribe(tuple->chatService.authorizeOperation(tuple.getT2().getChatId(), tuple.getT1().getId(), MessageOperation.READ));

        return messageRepository.findById(messageId)
                .zipWith(messageContentRepository.findByMessageId(messageId))
                .map(
                        mes -> MessageResponseDto.fromMessageWithContent(mes.getT1(), mes.getT2().get())
                );

    }

    @Override
    public Mono<PageImpl<Object>> getChatMessages(String username, UUID chatId, Pageable pageable) {
        personService.getPerson(username)
                .subscribe(personResponseDto -> chatService.authorizeOperation(chatId, personResponseDto.getId(), MessageOperation.READ));
        return messageRepository.findAllByChatIdOrderByTimestampDesc(chatId, pageable)
                .collectList()
                .map(
                        messages1 -> messageContentRepository.findByMessageIdIn(messages1.stream().map(Message::getId).toList())
                )
                .zipWith(this.messageRepository.count())
                .map(t -> new PageImpl<>(t.toList(), pageable, t.getT2()));

    }

    @Override
    public Mono<PageImpl<Object>> getMessageReplies(String username, UUID messageId, Pageable pageable) {
        personService.getPerson(username)
                .zipWith(messageRepository.findById(messageId))
                .subscribe(tuple -> chatService.authorizeOperation(tuple.getT2().getChatId(), tuple.getT1().getId(), MessageOperation.READ));

        return messageRepository.findAllByReplyIdOrderByTimestampDesc(messageId, pageable)
                .collectList()
                .map(
                        messages1 -> messageContentRepository.findByMessageIdIn(messages1.stream().map(Message::getId).toList())
                )
                .zipWith(this.messageRepository.count())
                .map(t -> new PageImpl<>(t.toList(), pageable, t.getT2()));
    }

    @Override
    public void authorizeOperationOnMessage(UUID messageId, UUID personId, MessageOperation operation) {
        messageRepository.findById(messageId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException()))
                .subscribe(m -> {
                    if (operation == MessageOperation.WRITE)
                        throw new IllegalAccessException();
                    else
                        chatService.authorizeOperation(m.getChatId(), personId, operation);//todo
                });
    }

    private void checkPersonIsAuthor(UUID personId, UUID messageId) {
        messageRepository.findById(messageId)
                .subscribe(msg -> {
                    if (!msg.getAuthorId().equals(personId)) throw new ResourceNotFoundException();
                });
    }
}
