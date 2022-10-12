package com.highload.chatservice.dto.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@AllArgsConstructor
@Data
public class GroupMemberResponseDto {
    private UUID groupId;
    private UUID personId;
    private boolean writePosts;
    private boolean writeComments;
    private boolean manageMembers;
}
