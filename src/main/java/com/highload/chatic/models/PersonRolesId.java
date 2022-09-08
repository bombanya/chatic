package com.highload.chatic.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class PersonRolesId implements Serializable {
//    @ManyToOne
//    @JoinColumn(name = "person_id")
//    private Person person;

    @Column(name = "person_id")
    private UUID personId;

//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    private AuthRole role;

    @Column(name = "role_id")
    private UUID roleId;

    public PersonRolesId(UUID personId, UUID roleId) {
        this.personId = personId;
        this.roleId = roleId;
    }

    protected PersonRolesId() {
    }

    /*public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }*/

    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }

    /*public AuthRole getRole() {
        return role;
    }

    public void setRole(AuthRole role) {
        this.role = role;
    }*/

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }
}
