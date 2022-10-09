package com.highload.chatservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GroupMemberId implements Serializable {
    @Column(name = "pgroup")
    private UUID pgroupId;
    @Column(name = "person")
    private UUID personId;
}
