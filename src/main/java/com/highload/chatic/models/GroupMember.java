package com.highload.chatic.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "groupmember", schema = "public", catalog = "chatic")
public class GroupMember {
    @EmbeddedId
    private GroupMemberId groupMemberId;

//    @ManyToOne
//    @JoinColumn(name = "role", referencedColumnName = "id")
//    private GroupRole role;

    @Column(name = "role")
    private UUID roleId;

    public GroupMember(UUID pgroupId, UUID personId, UUID roleId) {
        this.groupMemberId = new GroupMemberId(pgroupId, personId);
        this.roleId = roleId;
    }

    public GroupMember() {
    }

//    public PGroup getPgroup() {
//        return groupMemberId.getPgroup();
//    }

//    public void setPgroup(PGroup pgroup) {
//        groupMemberId.setPgroup(pgroup);
//    }

//    public Person getPerson() {
//        return groupMemberId.getPerson();
//    }

//    public void setPerson(Person person) {
//        groupMemberId.setPerson(person);
//    }

//    public GroupRole getRole() {
//        return role;
//    }

//    public void setRole(GroupRole role) {
//        this.role = role;
//    }

    public GroupMemberId getGroupMemberId() {
        return groupMemberId;
    }

    public void setGroupMemberId(GroupMemberId groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

}
