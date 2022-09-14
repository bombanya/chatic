package com.highload.chatic.dto;

import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupRoleDto {
    @BooleanFlag
    boolean writePosts;

    @BooleanFlag
    boolean writeComments;

    @BooleanFlag
    boolean manageMembers;
}
