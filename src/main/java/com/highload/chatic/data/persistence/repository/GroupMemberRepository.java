package com.highload.chatic.data.persistence.repository;

import com.highload.chatic.models.GroupMember;
import com.highload.chatic.models.GroupMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
}