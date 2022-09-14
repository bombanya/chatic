package com.highload.chatic.service;

import com.highload.chatic.dto.GroupRoleDto;
import com.highload.chatic.dto.group.GroupMemberDto;
import com.highload.chatic.dto.PageResponseDto;
import com.highload.chatic.dto.group.GroupMemberResponseDto;
import com.highload.chatic.dto.message.MessageResponseDto;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface GroupService {

    GroupMemberDto getGroupMemberInfo(UUID group, UUID personId);
    void addGroup(String username);
    PageResponseDto<GroupMemberResponseDto> getMembers(String username, UUID groupId, Pageable pageable);
    GroupMemberResponseDto getMember(String username, UUID groupId, UUID personId);
    void addMember(String username, UUID groupId, UUID personId, GroupRoleDto groupRoleDto);
    void deleteMember(String username, UUID groupId, UUID personId);
}
