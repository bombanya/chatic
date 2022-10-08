package com.highload.chatservice.repository;

import com.highload.chatservice.models.GroupMember;
import com.highload.chatservice.models.GroupMemberId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


public interface GroupMemberRepository extends ReactiveCrudRepository<GroupMember, GroupMemberId> {

    Mono<GroupMember> findByGroupMemberId(GroupMemberId groupMemberId);
}