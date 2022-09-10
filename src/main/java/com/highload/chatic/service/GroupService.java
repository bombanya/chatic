package com.highload.chatic.service;

import com.highload.chatic.dto.GroupMemberDto;

import java.util.UUID;

public interface GroupService {

    GroupMemberDto getGroupMemberInfo(UUID group, UUID personId);
}
