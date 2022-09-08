package com.highload.chatic.data.persistence.repository;

import com.highload.chatic.models.PGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PGroupRepository extends JpaRepository<PGroup, UUID> {
}