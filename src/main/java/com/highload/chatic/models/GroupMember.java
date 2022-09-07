package com.highload.chatic.models;

import javax.persistence.*;

@Entity
@Table(name = "groupmember", schema = "public", catalog = "chatic")
public class GroupMember {
    @EmbeddedId
    private GroupMemberId groupmemberId;

    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "id")
    private GroupRole role;

    public PGroup getPgroup() {
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
    }

    public GroupRole getRole() {
        return role;
    }

    public void setRole(GroupRole role) {
        this.role = role;
    }
}
