package com.highload.chatic.repository;

import com.highload.chatic.models.GroupRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupRoleRepository extends JpaRepository<GroupRole, UUID> {
}