package com.highload.messageservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Table(name = "messagecontent")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageContent implements Persistable<UUID> {

    @Id
    @Column("id")
    @NotNull
    private UUID id;

    @Column("message_id")
    @NotNull
    private UUID messageId;

    @NotEmpty
    private String text;

    @Override
    public boolean isNew() {
        return true;
    }
}
