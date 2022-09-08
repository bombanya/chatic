package com.highload.chatic.dto;

import com.highload.chatic.models.GroupMemberId;
import com.highload.chatic.models.GroupRole;
import com.highload.chatic.models.Person;
import com.highload.chatic.models.PGroup;

import javax.persistence.EmbeddedId;

public class GroupMemberDto {
    @EmbeddedId
    private GroupMemberId groupmemberId;

    private GroupRole role;

    /*public PGroup getPgroup() {
        return groupmemberId.getPgroup();
    }

    public void setPgroup(PGroup pgroup) {
        groupmemberId.setPgroup(pgroup);
    }

    public Person getPerson() {
        return groupmemberId.getPerson();
    }

    public void setPerson(Person person) {
        groupmemberId.setPerson(person);
    }*/

    public GroupRole getRole() {
        return role;
    }

    public void setRole(GroupRole role) {
        this.role = role;
    }
}
