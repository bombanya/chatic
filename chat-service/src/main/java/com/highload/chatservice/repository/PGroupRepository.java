package com.highload.chatservice.repository;

import com.highload.chatservice.models.PGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PGroupRepository extends JpaRepository<PGroup, UUID> {
}