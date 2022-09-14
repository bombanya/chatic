package com.highload.chatic.dto.group;

import com.highload.chatic.models.GroupMemberId;
import com.highload.chatic.models.GroupRole;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GroupMemberResponseDto {
    GroupMemberId groupMemberId;
    GroupRole role;
}
