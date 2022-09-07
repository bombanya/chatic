package com.highload.chatic.models;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class PersonrolesId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person personId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private AuthRole roleId;

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    public AuthRole getRoleId() {
        return roleId;
    }

    public void setRoleId(AuthRole roleId) {
        this.roleId = roleId;
    }
}
