package com.highload.chatic.service;

import com.highload.chatic.dto.person.PersonRequestDto;
import com.highload.chatic.dto.person.PersonResponseDto;
import com.highload.chatic.exception.IllegalAccessException;
import com.highload.chatic.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface PersonService {
    PersonResponseDto getPerson(UUID personId) throws ResourceNotFoundException;

    PersonResponseDto getPerson(String username) throws ResourceNotFoundException;

    void addPerson(String username, PersonRequestDto personRequestDto) throws ResourceNotFoundException, IllegalAccessException;

    void updatePerson(String username, PersonRequestDto person) throws ResourceNotFoundException, IllegalAccessException, IllegalAccessException;

    void deletePerson(String username, UUID personId) throws ResourceNotFoundException, IllegalAccessException;
}
