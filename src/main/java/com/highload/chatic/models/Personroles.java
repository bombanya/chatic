package com.highload.chatic.models;

import javax.persistence.*;

@Entity
@Table(name = "personroles")
public class Personroles {
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
