package com.highload.chatservice.repository;

import com.highload.chatservice.models.PGroup;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PGroupRepository extends ReactiveCrudRepository<PGroup, UUID> {
}