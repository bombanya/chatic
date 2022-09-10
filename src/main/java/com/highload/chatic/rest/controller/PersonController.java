package com.highload.chatic.rest.controller;

import com.highload.chatic.dto.person.PersonRequestDto;
import com.highload.chatic.dto.validation.AddRequest;
import com.highload.chatic.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
public class PersonController {

    private final PersonService personService;

    @PostMapping("/registration")
    @PreAuthorize("hasAuthority('ADMIN')")
    @SneakyThrows
    @Validated(AddRequest.class)
    public ResponseEntity<?> register(@RequestBody @Valid PersonRequestDto personRequestDto,
                                      BindingResult bindingResult,
                                      Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        personService.addPerson(authentication.getName(), personRequestDto);
        return ResponseEntity.ok().build();
    }
}
