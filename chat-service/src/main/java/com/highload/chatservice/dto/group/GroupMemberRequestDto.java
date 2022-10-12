package com.highload.chatservice.dto.group;

import jdk.jfr.BooleanFlag;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class GroupMemberRequestDto {

    @NotNull
    private UUID personId;

    @BooleanFlag
    private boolean writePosts;

    @BooleanFlag
    private boolean writeComments;

    @BooleanFlag
    private boolean manageMembers;
}
