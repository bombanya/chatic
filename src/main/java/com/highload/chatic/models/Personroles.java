package com.highload.chatic.models;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

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

    public Authrole getRoleId() {
        return personrolesId.getRoleId();
    }

    public void setRoleId(Authrole roleId) {
        personrolesId.setRoleId(roleId);
    }
}
