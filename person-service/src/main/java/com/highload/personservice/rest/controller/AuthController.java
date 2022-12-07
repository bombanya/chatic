package com.highload.personservice.rest.controller;

import com.highload.personservice.PersonServiceApplication;
import com.highload.personservice.dto.person.PersonAuthDto;
import com.highload.personservice.service.PersonService;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.log.Fields;
import io.opentracing.tag.Tags;
import io.opentracing.util.GlobalTracer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons-auth")
public class AuthController {
    Tracer tracer = PersonServiceApplication.initTracer("person-service-auth");
    private final PersonService personService;

    @GetMapping("/{username}")
    public PersonAuthDto getPerson(@PathVariable String username) {
        Span span = tracer.buildSpan("Auth").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            Tags.HTTP_METHOD.set(span, "GET");
            return personService.getPersonForAuth(username);
        } catch(Exception ex) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, ex, Fields.MESSAGE, ex.getMessage()));
        } finally {
            span.finish();
        }
        return null;
    }
}
