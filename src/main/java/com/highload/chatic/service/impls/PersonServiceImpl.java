package com.highload.chatic.service.impls;

import com.highload.chatic.models.Person;
import com.highload.chatic.repository.PersonRepository;
import com.highload.chatic.dto.person.PersonPageResponseDto;
import com.highload.chatic.dto.person.PersonRequestDto;
import com.highload.chatic.dto.person.PersonResponseDto;
import com.highload.chatic.exception.IllegalAccessException;
import com.highload.chatic.exception.ResourceNotFoundException;
import com.highload.chatic.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
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
    public PersonResponseDto getPerson(UUID personId) throws ResourceNotFoundException {
        var person = personRepository.findById(personId)
                .orElseThrow(ResourceNotFoundException::new);
        return modelMapper.map(person, PersonResponseDto.class);
    }

    @Override
    public PersonResponseDto getPerson(String username) throws ResourceNotFoundException {
        var person = personRepository.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
        return modelMapper.map(person, PersonResponseDto.class);
    }

    @Override
    public void registerNewPerson(PersonRequestDto personRequestDto) {
        personRequestDto.setPassword(passwordEncoder.encode(personRequestDto.getPassword()));
        personRepository.save(modelMapper.map(personRequestDto, Person.class));
    }

    @Override
    public PersonPageResponseDto getPersons(UserDetails userDetails, Pageable pageable) {
        //TODO
        return null;
    }

    @Override
    public void updatePerson(UserDetails userDetails, PersonRequestDto person) throws IllegalAccessException {
        //TODO
    }

    @Override
    public void deletePerson(UserDetails userDetails, UUID personId) throws IllegalAccessException {
        //TODO
    }
}
