package com.highload.chatic.service.impls;

import com.highload.chatic.dto.person.PersonRequestDto;
import com.highload.chatic.dto.person.PersonResponseDto;
import com.highload.chatic.exception.IllegalAccessException;
import com.highload.chatic.exception.ResourceNotFoundException;
import com.highload.chatic.models.AuthRoleName;
import com.highload.chatic.models.Person;
import com.highload.chatic.repository.PersonRepository;
import com.highload.chatic.service.PersonService;
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
    public void addPerson(String username, PersonRequestDto personRequestDto) throws ResourceNotFoundException, IllegalAccessException {
        if (!personCanAddOthers(username))
            throw new IllegalAccessException();
        personRequestDto.setPassword(passwordEncoder.encode(personRequestDto.getPassword()));
        personRepository.save(modelMapper.map(personRequestDto, Person.class));
    }

    @Override
    public void updatePerson(String username, PersonRequestDto personRequestDto) throws ResourceNotFoundException, IllegalAccessException {
        var personToUpdate = personRepository.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
        if (!personCanUpdateOthers(username, personToUpdate.getUsername())) {
            throw new IllegalAccessException();
        }
        personToUpdate = modelMapper.map(personRequestDto, Person.class);
        personToUpdate.setPassword(passwordEncoder.encode(personToUpdate.getPassword()));
        personRepository.save(personToUpdate);
    }

    @Override
    public void deletePerson(String username, UUID personId) throws ResourceNotFoundException, IllegalAccessException {
        var personToDelete = personRepository.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
        if (!personCanDeleteOthers(username, personToDelete.getUsername())) {
            throw new IllegalAccessException();
        }
        personRepository.delete(personToDelete);
    }

    private boolean personCanAddOthers(String username) throws ResourceNotFoundException {
//        var person = personRepository.findByUsername(username)
//                .orElseThrow(ResourceNotFoundException::new);
//        return person.getAuthRole() == AuthRoleName.ADMIN;
        return true;
    }

    private boolean personCanDeleteOthers(String username, String otherUsername) throws ResourceNotFoundException {
        var person = personRepository.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
        return person.getAuthRole() == AuthRoleName.ADMIN || username.equals(otherUsername);
    }

    private boolean personCanUpdateOthers(String username, String otherUsername) throws ResourceNotFoundException {
        return username.equals(otherUsername);
    }

}
