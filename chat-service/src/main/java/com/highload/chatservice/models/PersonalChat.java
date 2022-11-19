package com.highload.chatservice.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "personalchat")
@PrimaryKeyJoinColumn(name = "id")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalChat extends Chat {

    @Column(name = "person1")
    @NotNull(message = "Не указан участник (1) чата")
    private UUID person1Id;

    @Column(name = "person2")
    @NotNull(message = "Не указан участник (2) чата")
    private UUID person2Id;
}
