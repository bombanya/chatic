package com.highload.chatservice.service;

import com.highload.chatservice.dto.PageResponseDto;
import com.highload.chatservice.dto.group.GroupMemberRequestDto;
import com.highload.chatservice.dto.group.GroupMemberResponseDto;
import com.highload.chatservice.dto.group.GroupRequestDto;
import com.highload.chatservice.dto.group.GroupResponseDto;
import com.highload.chatservice.models.ChatOperation;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GroupService {
    Mono<GroupResponseDto> addGroup(String username, GroupRequestDto groupRequestDto);
    Mono<PageResponseDto<GroupMemberResponseDto>> getMembers(String username, UUID groupId, Pageable pageable);
    Mono<GroupMemberResponseDto> getMember(String username, UUID groupId, UUID personId);
    Mono<GroupMemberResponseDto> addMember(String username, UUID groupId, GroupMemberRequestDto groupMemberRequestDto);
    Mono<Void> updateMember(String username, UUID groupId, GroupMemberRequestDto requestDto);
    Mono<Void> deleteMember(String username, UUID groupId, UUID personId);
    Mono<Void> authorizeOperation(UUID groupId, UUID personId, ChatOperation operation);
    Mono<PageResponseDto<GroupResponseDto>> getAllGroups(String username, Pageable pageable);

}
