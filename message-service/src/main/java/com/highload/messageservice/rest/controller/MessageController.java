package com.highload.messageservice.rest.controller;

import com.highload.messageservice.dto.message.MessageRequestDto;
import com.highload.messageservice.dto.message.MessageResponseDto;
import com.highload.messageservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<?> addMessage(@RequestBody @Valid MessageRequestDto messageRequestDto,
                              @RequestHeader("USERNAME") String username) {
        return service.addMessage(username, messageRequestDto);
    }

    @PostMapping("/{messageId}/replies")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<?> addReply(@PathVariable UUID messageId,
                            @RequestBody @Valid MessageRequestDto messageRequestDto,
                            @RequestHeader("USERNAME") String username) {
        return service.addReply(username, messageId, messageRequestDto);
    }

    @PutMapping("/{messageId}")
    public Mono<?> updateMessage(@PathVariable UUID messageId,
                                 @RequestBody @Valid MessageRequestDto messageRequestDto,
                                 @RequestHeader("USERNAME") String username) {
        return service.updateMessage(username, messageId, messageRequestDto);
    }

    @DeleteMapping("/{messageId}")
    public Mono<?> deleteMessage(@PathVariable UUID messageId,
                                 @RequestHeader("USERNAME") String username) {
        return service.deleteMessage(username, messageId);
    }

    @GetMapping("/{messageId}")
    public Mono<MessageResponseDto> getMessage(@PathVariable UUID messageId,
                                               @RequestHeader("USERNAME") String username) {
        return service.getMessage(username, messageId);
    }

    @GetMapping
    public Mono<PageImpl<MessageResponseDto>> getMessages(@RequestParam UUID chatId,
                                                          @RequestParam(defaultValue = "0", required = false) int page,
                                                          @RequestParam(defaultValue = "10", required = false) int size,
                                                          @RequestHeader("USERNAME") String username) {
        return service.getChatMessages(username, chatId, PageRequest.of(page, size));
    }

    @GetMapping("/{messageId}/replies")
    public Mono<PageImpl<MessageResponseDto>> getReplies(@PathVariable UUID messageId,
                                                         @RequestParam(defaultValue = "0", required = false) int page,
                                                         @RequestParam(defaultValue = "10", required = false) int size,
                                                         @RequestHeader("USERNAME") String username) {
        return service.getMessageReplies(username, messageId, PageRequest.of(page, size));
    }
}
