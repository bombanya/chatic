package com.highload.chatic.dto;

import com.highload.chatic.models.AuthRole;
import com.highload.chatic.models.Person;
import com.highload.chatic.models.PersonRolesId;

import javax.persistence.EmbeddedId;

public class PersonRolesDto {
    @EmbeddedId
    private PersonRolesId personrolesId;

    /*public Person getPerson() {
        return personrolesId.getPerson();
    }

    public void setPerson(Person personId) {
        personrolesId.setPerson(personId);
    }

    public AuthRole getRole() {
        return personrolesId.getRole();
    }

    public void setRole(AuthRole roleId) {
        personrolesId.setRole(roleId);
    }*/
}
