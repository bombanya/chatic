package com.highload.chatservice.repository;

import com.highload.chatservice.models.GroupRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupRoleRepository extends JpaRepository<GroupRole, UUID> {
}