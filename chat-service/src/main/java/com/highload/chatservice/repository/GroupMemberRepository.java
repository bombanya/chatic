package com.highload.chatservice.repository;

import com.highload.chatservice.models.GroupMember;
import com.highload.chatservice.models.GroupMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {

    Optional<GroupMember> findByGroupMemberId(GroupMemberId groupMemberId);
}