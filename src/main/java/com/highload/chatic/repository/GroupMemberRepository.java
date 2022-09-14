package com.highload.chatic.repository;

import com.highload.chatic.models.GroupMember;
import com.highload.chatic.models.GroupMemberId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {

    Optional<GroupMember> findByGroupMemberId(GroupMemberId groupMemberId);

    @Query (
            "select g from GroupMember g " +
                    "where g.groupMemberId.pgroupId = :groupId"
    )
    Page<GroupMember> findGroupMembers(UUID groupId, Pageable pageable);

    @Query (
            "select g from GroupMember g " +
                    "where g.groupMemberId.personId = :personId"
    )
    Optional<GroupMember> findByPersonId(UUID personId);
}