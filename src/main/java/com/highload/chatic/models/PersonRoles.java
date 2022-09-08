package com.highload.chatic.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "personroles")
public class PersonRoles {
    @EmbeddedId
    private PersonRolesId personRolesId;

    public PersonRoles(UUID personId, UUID roleId) {
        this.personRolesId = new PersonRolesId(personId, roleId);
    }

    protected PersonRoles() {
    }

    public PersonRolesId getPersonRolesId() {
        return personRolesId;
    }
}
