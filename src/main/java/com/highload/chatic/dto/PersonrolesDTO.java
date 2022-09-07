package com.highload.chatic.dto;

import com.highload.chatic.models.Authrole;
import com.highload.chatic.models.Person;
import com.highload.chatic.models.PersonrolesId;

import javax.persistence.EmbeddedId;

public class PersonrolesDTO {
    @EmbeddedId
    private PersonrolesId personrolesId;

    public Person getPersonId() {
        return personrolesId.getPersonId();
    }

    public void setPersonId(Person personId) {
        personrolesId.setPersonId(personId);
    }

    public Authrole getRoleId() {
        return personrolesId.getRoleId();
    }

    public void setRoleId(Authrole roleId) {
        personrolesId.setRoleId(roleId);
    }
}
