package com.highload.messageservice.rest.controller;

import com.highload.messageservice.dto.message.MessageRequestDto;
import com.highload.messageservice.dto.message.MessageResponseDto;
import com.highload.messageservice.models.MessageContent;
import com.highload.messageservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> addMessage(@RequestBody @Valid MessageRequestDto messageRequestDto, Principal principal) {
        return service.addMessage(principal.getName(), messageRequestDto);
    }

    @PostMapping("{messageId}/replies")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> addReply(@PathVariable UUID messageId,
                               @RequestBody @Valid MessageRequestDto messageRequestDto,
                               Principal principal) {
        return service.addReply(principal.getName(), messageId, messageRequestDto);
    }

    @PutMapping("{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<MessageContent> updateMessage(@PathVariable UUID messageId,
                                              @RequestBody @Valid MessageRequestDto messageRequestDto,
                                              Principal principal) {
        return service.updateMessage(principal.getName(), messageId, messageRequestDto);
    }

    @DeleteMapping("{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteMessage(@PathVariable UUID messageId, Principal principal) {
        return service.deleteMessage(principal.getName(), messageId);
    }

    @GetMapping("{messageId}")
    public Mono<MessageResponseDto> getMessage(@PathVariable UUID messageId, Principal principal) {
        return service.getMessage(principal.getName(), messageId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<PageImpl<Object>> getMessages(@RequestParam UUID chatId,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              Principal principal) {
        return service.getChatMessages(principal.getName(), chatId, PageRequest.of(page, size));
    }

    @GetMapping("{messageId}/replies")
    @ResponseStatus(HttpStatus.OK)
    public Mono<PageImpl<Object>> getReplies(
            @PathVariable UUID messageId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal
    ) {
        return service.getMessageReplies(principal.getName(), messageId, PageRequest.of(page, size));
    }
}
