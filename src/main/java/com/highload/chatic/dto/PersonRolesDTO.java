package com.highload.chatic.dto;

import com.highload.chatic.models.AuthRole;
import com.highload.chatic.models.Person;
import com.highload.chatic.models.PersonrolesId;

import javax.persistence.EmbeddedId;

public class PersonRolesDTO {
    @EmbeddedId
    private PersonrolesId personrolesId;

    public Person getPersonId() {
        return personrolesId.getPersonId();
    }

    public void setPersonId(Person personId) {
        personrolesId.setPersonId(personId);
    }

    public AuthRole getRoleId() {
        return personrolesId.getRoleId();
    }

    public void setRoleId(AuthRole roleId) {
        personrolesId.setRoleId(roleId);
    }
}
