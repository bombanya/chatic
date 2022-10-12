package com.highload.chatservice.service;

import com.highload.chatservice.dto.PageResponseDto;
import com.highload.chatservice.dto.group.GroupMemberRequestDto;
import com.highload.chatservice.dto.group.GroupMemberResponseDto;
import com.highload.chatservice.dto.group.GroupRequestDto;
import com.highload.chatservice.dto.group.GroupResponseDto;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GroupService {
    Mono<GroupMemberResponseDto> getGroupMemberInfo(UUID group, UUID personId);
    Mono<GroupResponseDto> addGroup(String username, GroupRequestDto groupRequestDto);
    Mono<PageResponseDto<GroupMemberResponseDto>> getMembers(String username, UUID groupId, Pageable pageable);
    Mono<GroupMemberResponseDto> getMember(String username, UUID groupId, UUID personId);
    Mono<GroupMemberResponseDto> addMember(String username, UUID groupId, GroupMemberRequestDto groupMemberRequestDto);
    Mono<GroupMemberResponseDto> updateMember(String username, UUID groupId, GroupMemberRequestDto requestDto);
    Mono<Void> deleteMember(String username, UUID groupId, UUID personId);

}
