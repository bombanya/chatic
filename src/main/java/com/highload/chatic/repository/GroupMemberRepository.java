package com.highload.chatic.repository;

import com.highload.chatic.models.GroupMember;
import com.highload.chatic.models.GroupMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {

    Optional<GroupMember> findByGroupMemberId(GroupMemberId groupMemberId);
}