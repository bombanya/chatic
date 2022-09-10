package com.highload.chatic.dto;

import com.highload.chatic.models.GroupMemberId;
import com.highload.chatic.models.GroupRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupMemberDto {
    private GroupMemberId groupmemberId;
    private GroupRole role;
}
