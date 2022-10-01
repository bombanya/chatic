package com.highload.userservice.service.impls;

import com.highload.userservice.dto.person.PersonRequestDto;
import com.highload.userservice.dto.person.PersonResponseDto;
import com.highload.userservice.exception.ResourceNotFoundException;
import com.highload.userservice.models.Person;
import com.highload.userservice.repository.PersonRepository;
import com.highload.userservice.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PersonResponseDto getPerson(UUID personId) {
        var person = personRepository.findById(personId)
                .orElseThrow(ResourceNotFoundException::new);
        return modelMapper.map(person, PersonResponseDto.class);
    }

    @Override
    public PersonResponseDto getPerson(String username) {
        var person = personRepository.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
        return modelMapper.map(person, PersonResponseDto.class);
    }

    @Override
    public void addPerson(PersonRequestDto personRequestDto) {
        personRequestDto.setPassword(passwordEncoder.encode(personRequestDto.getPassword()));
        personRepository.save(modelMapper.map(personRequestDto, Person.class));
    }

    @Override
    public void updatePerson(PersonRequestDto person) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deletePerson(String username) {
        var personToDelete = personRepository.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
        personRepository.delete(personToDelete);
    }

}
