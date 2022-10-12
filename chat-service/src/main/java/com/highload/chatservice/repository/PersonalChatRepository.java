package com.highload.chatservice.repository;

import com.highload.chatservice.models.PersonalChat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PersonalChatRepository extends JpaRepository<PersonalChat, UUID> {
    @Query("select chat from PersonalChat chat " +
            "where chat.person1Id = :personId or chat.person2Id = :personId")
    Page<PersonalChat> findAllUserChats(UUID personId, Pageable pageable);

    @Query("select chat from PersonalChat chat " +
            "where (chat.person1Id = :person1 and chat.person2Id = :person2) " +
            "or (chat.person2Id = :person1 and chat.person1Id = :person2)")
    Optional<PersonalChat> findByPerson1IdAndPerson2Id(UUID person1, UUID person2);
}
