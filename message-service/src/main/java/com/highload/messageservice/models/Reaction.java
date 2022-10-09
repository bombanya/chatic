package com.highload.messageservice.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Table(name = "reaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reaction implements Persistable<UUID> {

    @Id
    @NotNull
    private UUID id;

    @Column("message")
    @NotNull
    private UUID messageId;

    @Column("person")
    @NotNull
    private UUID personId;

    @Column("emoji")
    @NotNull(message = "Должна быть задана реакция")
    private Emoji emoji;

    private boolean isNew = false;

    @Override
    public boolean isNew() {
        return isNew;
    }
}
