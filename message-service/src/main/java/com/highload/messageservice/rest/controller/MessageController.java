package com.highload.messageservice.rest.controller;

import com.highload.messageservice.dto.PageResponseDto;
import com.highload.messageservice.dto.message.MessageRequestDto;
import com.highload.messageservice.dto.message.MessageResponseDto;
import com.highload.messageservice.service.MessageService;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.log.Fields;
import io.opentracing.tag.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import io.opentracing.util.GlobalTracer;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    Tracer tracer = GlobalTracer.get();
    private final MessageService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MessageResponseDto> addMessage(@RequestBody @Valid MessageRequestDto messageRequestDto,
                                               @RequestHeader("USERNAME") String username) {
        Span span = tracer.buildSpan("addMessage").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            Tags.HTTP_METHOD.set(span, "POST");
            return service.addMessage(username, messageRequestDto);
        } catch(Exception ex) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, ex, Fields.MESSAGE, ex.getMessage()));
        } finally {
            span.finish();
        }
        return null;
    }

    @PostMapping("/{messageId}/replies")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MessageResponseDto> addReply(@PathVariable UUID messageId,
                                             @RequestBody @Valid MessageRequestDto messageRequestDto,
                                             @RequestHeader("USERNAME") String username) {
        Span span = tracer.buildSpan("addReply").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            Tags.HTTP_METHOD.set(span, "POST");
            return service.addReply(username, messageId, messageRequestDto);
        } catch(Exception ex) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, ex, Fields.MESSAGE, ex.getMessage()));
        } finally {
            span.finish();
        }
        return null;
    }

    @PutMapping("/{messageId}")
    public Mono<Void> updateMessage(@PathVariable UUID messageId,
                                    @RequestBody @Valid MessageRequestDto messageRequestDto,
                                    @RequestHeader("USERNAME") String username) {
        Span span = tracer.buildSpan("updateMessage").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            Tags.HTTP_METHOD.set(span, "PUT");
            return service.updateMessage(username, messageId, messageRequestDto);
        } catch(Exception ex) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, ex, Fields.MESSAGE, ex.getMessage()));
        } finally {
            span.finish();
        }
        return null;
    }

    @DeleteMapping("/{messageId}")
    public Mono<Void> deleteMessage(@PathVariable UUID messageId,
                                    @RequestHeader("USERNAME") String username) {
        Span span = tracer.buildSpan("deleteMessage").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            Tags.HTTP_METHOD.set(span, "DELETE");
            return service.deleteMessage(username, messageId);
        } catch(Exception ex) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, ex, Fields.MESSAGE, ex.getMessage()));
        } finally {
            span.finish();
        }
        return null;
    }

    @GetMapping("/{messageId}")
    public Mono<MessageResponseDto> getMessage(@PathVariable UUID messageId,
                                               @RequestHeader("USERNAME") String username) {
        Span span = tracer.buildSpan("getMessage").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            Tags.HTTP_METHOD.set(span, "GET");
            return service.getMessage(username, messageId);
        } catch(Exception ex) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, ex, Fields.MESSAGE, ex.getMessage()));
        } finally {
            span.finish();
        }
        return null;
    }

    @GetMapping
    public Mono<PageResponseDto<MessageResponseDto>> getMessages(@RequestParam UUID chatId,
                                                                 @RequestParam(defaultValue = "0", required = false) int page,
                                                                 @RequestParam(defaultValue = "10", required = false) int size,
                                                                 @RequestHeader("USERNAME") String username) {
        Span span = tracer.buildSpan("getMessages").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            Tags.HTTP_METHOD.set(span, "GET");
            return service.getChatMessages(username, chatId, PageRequest.of(page, size));
        } catch(Exception ex) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, ex, Fields.MESSAGE, ex.getMessage()));
        } finally {
            span.finish();
        }
        return null;
    }

    @GetMapping("/{messageId}/replies")
    public Mono<PageResponseDto<MessageResponseDto>> getReplies(@PathVariable UUID messageId,
                                                                @RequestParam(defaultValue = "0", required = false) int page,
                                                                @RequestParam(defaultValue = "10", required = false) int size,
                                                                @RequestHeader("USERNAME") String username) {
        return service.getMessageReplies(username, messageId, PageRequest.of(page, size));
    }
}
