package com.highload.chatic.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "groupmember", schema = "public", catalog = "chatic")
public class Groupmember {
    @EmbeddedId
    private GroupmemberId groupmemberId;

    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "id")
    private Grouprole role;

    public Pgroup getPgroup() {
        return groupmemberId.getPgroup();
    }

    public void setPgroup(Pgroup pgroup) {
        groupmemberId.setPgroup(pgroup);
    }

    public Person getPerson() {
        return groupmemberId.getPerson();
    }

    public void setPerson(Person person) {
        groupmemberId.setPerson(person);
    }

    public Grouprole getRole() {
        return role;
    }

    public void setRole(Grouprole role) {
        this.role = role;
    }
}
