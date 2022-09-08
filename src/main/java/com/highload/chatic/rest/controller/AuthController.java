package com.highload.chatic.rest.controller;

import com.highload.chatic.models.Person;
import com.highload.chatic.security.PersonDto;
import com.highload.chatic.security.RegistrationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {
    private final RegistrationService registrationService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthController(RegistrationService registrationService,
                          ModelMapper modelMapper) {
        this.registrationService = registrationService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    public void login() {}

    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody @Valid PersonDto personDto,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Not correct person to create");
        }
        Person person = convertToPerson(personDto);
        registrationService.register(person);

        return ResponseEntity.ok().body("OK");
    }

    public Person convertToPerson(PersonDto personDto) {
        return this.modelMapper.map(personDto, Person.class);
    }
}
