package com.highload.chatic.repository;

import com.highload.chatic.models.PersonalChat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonalChatRepository extends JpaRepository<PersonalChat, UUID> {
    @Query(
            "select chat from PersonalChat chat " +
            "where chat.person1Id = :personId or chat.person2Id = :personId"
    )
    Page<PersonalChat> findAllUserChats(UUID personId, Pageable pageable);


    @Query(
            "select chat from PersonalChat chat " +
            "where chat.id = :id and chat.person1Id <> :userId and chat.person2Id <> :userId"
    )
    boolean isNotUserChat(UUID id, UUID userId);
}
