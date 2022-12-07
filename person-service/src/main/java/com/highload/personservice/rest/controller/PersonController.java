package com.highload.personservice.rest.controller;

import com.highload.personservice.PersonServiceApplication;
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
    Tracer tracer = PersonServiceApplication.initTracer("person-service-person");
    private final PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(AddRequest.class)
    @PreAuthorize("#role == 'SCOPE_ADMIN'")
    public void addPerson(@RequestBody @Valid PersonRequestDto personRequestDto,
                          @RequestHeader("ROLE") String role) {
        Span span = tracer.buildSpan("addPerson").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            Tags.HTTP_METHOD.set(span, "POST");
            personService.addPerson(personRequestDto);
        } catch(Exception ex) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, ex, Fields.MESSAGE, ex.getMessage()));
        } finally {
            span.finish();
        }
    }

    @GetMapping("/byusername/{username}")
    public PersonResponseDto getPerson(@PathVariable String username) {
        Span span = tracer.buildSpan("getPerson by username").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            Tags.HTTP_METHOD.set(span, "GET");
            return personService.getPerson(username);
        } catch(Exception ex) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, ex, Fields.MESSAGE, ex.getMessage()));
        } finally {
            span.finish();
        }
        return null;
    }

    @GetMapping("/byid/{userId}")
    public PersonResponseDto getPerson(@PathVariable UUID userId) {
        Span span = tracer.buildSpan("getPerson by id").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            Tags.HTTP_METHOD.set(span, "GET");
            return personService.getPerson(userId);
        } catch(Exception ex) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, ex, Fields.MESSAGE, ex.getMessage()));
        } finally {
            span.finish();
        }
        return null;
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("#role == 'SCOPE_ADMIN' or #username == #name")
    public void deletePerson(@PathVariable String username,
                             @RequestHeader("ROLE") String role,
                             @RequestHeader("USERNAME") String name) {
        Span span = tracer.buildSpan("deletePerson").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            Tags.SPAN_KIND.set(span, Tags.SPAN_KIND_CLIENT);
            Tags.HTTP_METHOD.set(span, "DELETE");
            personService.deletePerson(username);
        } catch(Exception ex) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, ex, Fields.MESSAGE, ex.getMessage()));
        } finally {
            span.finish();
        }
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
