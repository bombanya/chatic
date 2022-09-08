package com.highload.chatic.rest.service;

import com.highload.chatic.dto.person.PersonPageResponseDto;
import com.highload.chatic.dto.person.PersonRequestDto;
import com.highload.chatic.dto.person.PersonResponseDto;
import com.highload.chatic.exception.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface PersonService {
    PersonResponseDto getPerson(UUID personId) throws ResourceNotFoundException;

    PersonResponseDto getPerson(String username) throws ResourceNotFoundException;

    PersonPageResponseDto getPersons(UserDetails userDetails, Pageable pageable);

    PersonResponseDto addPerson(UserDetails userDetails, PersonRequestDto person);

    void updatePerson(UserDetails userDetails, PersonRequestDto person) throws ResourceNotFoundException, com.highload.chatic.exception.IllegalAccessException;

    void deletePerson(UserDetails userDetails, UUID personId) throws ResourceNotFoundException, com.highload.chatic.exception.IllegalAccessException;
}
