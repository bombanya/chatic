package com.highload.chatservice.repository;

import com.highload.chatservice.models.PersonalChat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface PersonalChatRepository extends ReactiveCrudRepository<PersonalChat, UUID> {
    @Query(
            "select * from personalchat " +
                    "where person1 = :personId or person2 = :personId"
    )
    Flux<PersonalChat> findAllUserChats(UUID personId, Pageable pageable);


    @Query(
            "select * from personalchat " +
                    "where id = :id and person1 <> :userId and person2 <> :userId"
    )
    boolean isNotUserChat(UUID id, UUID userId);

    @Query("select * from personalchat " +
            "where (person1 = :person1 and person2 = :person2) " +
            "or (person2 = :person1 and person1 = :person2)")
    Mono<PersonalChat> findByPerson1IdAndPerson2Id(UUID person1, UUID person2);
}
