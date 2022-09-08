package com.highload.chatic.data.persistence.repository;

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
            value = "select * from PersonalChat where person1 = :personId or person2 = :personId \n-- #pageable\n",
            countQuery = "select count(*) from PersonalChat where person1 = :personId or person2 = :personId",
            nativeQuery = true
    )
    Page<PersonalChat> findAllByPerson1IdOrPerson2Id(UUID personId, Pageable pageable);

    @Query(
            value = "select * from PersonalChat c " +
                    "where c.id = :id and " +
                    "(c.person1 = :userId or c.person2 = :userId)",
            nativeQuery = true
    )
    boolean isNotUserChat(UUID id, UUID userId);
}
