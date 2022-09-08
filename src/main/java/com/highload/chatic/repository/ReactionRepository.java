package com.highload.chatic.repository;

import com.highload.chatic.models.Reaction;
import com.highload.chatic.models.ReactionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, ReactionId> {
}