package com.highload.personservice.rest.controller;

import com.highload.personservice.dto.person.PersonAuthDto;
import com.highload.personservice.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons-auth")
public class AuthController {

    private final PersonService personService;

    @GetMapping("/{username}")
    public PersonAuthDto getPerson(@PathVariable String username) {
        return personService.getPersonForAuth(username);
    }
}
