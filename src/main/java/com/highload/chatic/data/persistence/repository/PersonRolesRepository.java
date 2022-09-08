package com.highload.chatic.data.persistence.repository;

import com.highload.chatic.models.PersonRoles;
import com.highload.chatic.models.PersonRolesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PersonRolesRepository extends JpaRepository<PersonRoles, PersonRolesId> {
    List<PersonRoles> findAllByPersonRolesIdPersonId(UUID id);
}