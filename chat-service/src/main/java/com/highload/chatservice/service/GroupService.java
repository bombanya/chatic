package com.highload.chatservice.service;

import com.highload.chatservice.dto.GroupMemberDto;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GroupService {

    Mono<GroupMemberDto> getGroupMemberInfo(UUID group, UUID personId);
}
