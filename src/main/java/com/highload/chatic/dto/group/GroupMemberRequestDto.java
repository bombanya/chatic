package com.highload.chatic.dto.group;

import com.highload.chatic.models.GroupRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupMemberRequestDto {
    private String username;
    private GroupRole role;
}
