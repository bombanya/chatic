package com.highload.chatic.service.impls;

import com.highload.chatic.dto.person.PersonRequestDto;
import com.highload.chatic.dto.person.PersonResponseDto;
import com.highload.chatic.exception.ResourceNotFoundException;
import com.highload.chatic.models.Person;
import com.highload.chatic.repository.GroupMemberRepository;
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
    private final GroupMemberRepository groupMemberRepository;

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
        var groupMember = groupMemberRepository.findByPersonId(personToDelete.getId());
        groupMember.ifPresent(groupMemberRepository::delete);
        personRepository.delete(personToDelete);
    }

}
