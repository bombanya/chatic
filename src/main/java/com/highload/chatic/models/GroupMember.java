package com.highload.chatic.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "groupmember", schema = "public", catalog = "chatic")
@Getter
@Setter
public class GroupMember {
    @EmbeddedId
    private GroupMemberId groupMemberId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role", nullable = false)
    private GroupRole role;

}
