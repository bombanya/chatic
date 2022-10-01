package com.highload.chatservice.dto;

import com.highload.chatservice.models.GroupMemberId;
import com.highload.chatservice.models.GroupRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupMemberDto {
    private GroupMemberId groupmemberId;
    private GroupRole role;
}
