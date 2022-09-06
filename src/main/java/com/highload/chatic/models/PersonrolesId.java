package com.highload.chatic.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class PersonrolesId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person personId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Authrole roleId;

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    public Authrole getRoleId() {
        return roleId;
    }

    public void setRoleId(Authrole roleId) {
        this.roleId = roleId;
    }
}
