package com.highload.chatservice.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "groupmember")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupMember {
    @EmbeddedId
    private GroupMemberId groupMemberId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role", nullable = false)
    private GroupRole role;

}
