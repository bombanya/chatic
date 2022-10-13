package com.highload.chatservice.repository;

import com.highload.chatservice.models.GroupMember;
import com.highload.chatservice.models.GroupMemberId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;


public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {

    Optional<GroupMember> findByGroupMemberId(GroupMemberId groupMemberId);

    @Query("select groupMember from GroupMember groupMember " +
            "where groupMember.groupMemberId.pgroupId = :groupId")
    Page<GroupMember> findAllByGroupId(UUID groupId, Pageable pageable);

    @Query("select groupMember from GroupMember groupMember " +
            "where groupMember.groupMemberId.personId = :personId")
    Page<GroupMember> findAllByUserId(UUID personId, Pageable pageable);
}