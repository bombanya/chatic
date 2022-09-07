package com.highload.chatic.dto;

import com.highload.chatic.models.GroupmemberId;
import com.highload.chatic.models.Grouprole;
import com.highload.chatic.models.Person;
import com.highload.chatic.models.Pgroup;

import javax.persistence.EmbeddedId;

public class GroupmemberDTO {
    @EmbeddedId
    private GroupmemberId groupmemberId;

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
