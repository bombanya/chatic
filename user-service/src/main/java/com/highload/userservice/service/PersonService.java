package com.highload.userservice.service;

import com.highload.userservice.dto.person.PersonRequestDto;
import com.highload.userservice.dto.person.PersonResponseDto;

import java.util.UUID;

public interface PersonService {
    PersonResponseDto getPerson(UUID personId);

    PersonResponseDto getPerson(String username);

    void addPerson(PersonRequestDto personRequestDto);

    void updatePerson(PersonRequestDto person);

    void deletePerson(String username);
}
