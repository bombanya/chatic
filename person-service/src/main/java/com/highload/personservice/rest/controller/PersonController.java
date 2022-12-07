package com.highload.personservice.rest.controller;

import com.highload.personservice.dto.person.PersonRequestDto;
import com.highload.personservice.dto.person.PersonResponseDto;
import com.highload.personservice.dto.validation.AddRequest;
import com.highload.personservice.service.PersonService;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.log.Fields;
import io.opentracing.tag.Tags;
import io.opentracing.util.GlobalTracer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/persons")
public class PersonController {
    Tracer tracer = GlobalTracer.get();
    private final PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(AddRequest.class)
    @PreAuthorize("#role == 'SCOPE_ADMIN'")
    public void addPerson(@RequestBody @Valid PersonRequestDto personRequestDto,
                          @RequestHeader("ROLE") String role) {
        personService.addPerson(personRequestDto);
    }

    @GetMapping("/byusername/{username}")
    public PersonResponseDto getPerson(@PathVariable String username) {
        return personService.getPerson(username);
    }

    @GetMapping("/byid/{userId}")
    public PersonResponseDto getPerson(@PathVariable UUID userId) {
        return personService.getPerson(userId);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("#role == 'SCOPE_ADMIN' or #username == #name")
    public void deletePerson(@PathVariable String username,
                             @RequestHeader("ROLE") String role,
                             @RequestHeader("USERNAME") String name) {
        personService.deletePerson(username);
    }

    @GetMapping("/test")
    public String test() {
        Span span = tracer.buildSpan("test").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            return "Hello, World!";
        } catch(Exception ex) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, ex, Fields.MESSAGE, ex.getMessage()));
        } finally {
            span.finish();
        }
        return null;
    }
}
