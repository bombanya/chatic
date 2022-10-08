package com.highload.chatservice.repository;

import com.highload.chatservice.models.GroupRole;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupRoleRepository extends ReactiveCrudRepository<GroupRole, UUID> {
}