package com.highload.personservice.rest.controller;

import com.highload.personservice.dto.person.PersonRequestDto;
import com.highload.personservice.dto.person.PersonResponseDto;
import com.highload.personservice.dto.validation.AddRequest;
import com.highload.personservice.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(AddRequest.class)
    public void addPerson(@RequestBody @Valid PersonRequestDto personRequestDto) {
        personService.addPerson(personRequestDto);
    }

    @GetMapping("{username}")
    @ResponseStatus(HttpStatus.OK)
    public PersonResponseDto getPerson(@PathVariable String username) {
        return personService.getPerson(username);
    }

    @DeleteMapping("{username}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or #username == #principal.name")
    public void deletePerson(@PathVariable String username, Principal principal) {
        personService.deletePerson(username);
    }
}
