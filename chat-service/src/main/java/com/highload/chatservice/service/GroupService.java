package com.highload.chatservice.service;

import com.highload.chatservice.dto.GroupMemberDto;

import java.util.UUID;

public interface GroupService {

    GroupMemberDto getGroupMemberInfo(UUID group, UUID personId);
}
