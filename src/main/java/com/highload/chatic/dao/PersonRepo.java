package com.highload.chatic.dao;

import com.highload.chatic.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepo extends JpaRepository<Person, UUID> {

    Optional<Person> findByUsername(String username);
}
