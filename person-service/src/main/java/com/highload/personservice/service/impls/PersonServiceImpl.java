package com.highload.personservice.service.impls;

import com.highload.personservice.dto.person.PersonAuthDto;
import com.highload.personservice.dto.person.PersonRequestDto;
import com.highload.personservice.dto.person.PersonResponseDto;
import com.highload.personservice.exception.ResourceNotFoundException;
import com.highload.personservice.models.Person;
import com.highload.personservice.repository.PersonRepository;
import com.highload.personservice.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PersonResponseDto getPerson(UUID personId) {
        log.info("get person with uuid: {}", personId);
        var person = personRepository.findById(personId)
                .orElseThrow(ResourceNotFoundException::new);
        return modelMapper.map(person, PersonResponseDto.class);
    }

    @Override
    public PersonResponseDto getPerson(String username) {
        log.info("get person with username: {}", username);
        var person = personRepository.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
        return modelMapper.map(person, PersonResponseDto.class);
    }

    @Override
    public PersonAuthDto getPersonForAuth(String username) {
        log.info("get person for auth with username: {}", username);
        var person = personRepository.findByUsernameForAuth(username)
                .orElseThrow(ResourceNotFoundException::new);
        return modelMapper.map(person, PersonAuthDto.class);
    }

    @Override
    public void addPerson(PersonRequestDto personRequestDto) {
        log.info("add person with username: {}", personRequestDto.getUsername());
        personRequestDto.setPassword(passwordEncoder.encode(personRequestDto.getPassword()));
        personRepository.save(modelMapper.map(personRequestDto, Person.class));
    }

    @Override
    public void updatePerson(PersonRequestDto person) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deletePerson(String username) {
        log.info("delete person with username: {}", username);
        var personToDelete = personRepository.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
        personRepository.delete(personToDelete);
    }

}
