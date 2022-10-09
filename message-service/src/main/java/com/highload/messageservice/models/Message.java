package com.highload.messageservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Table(name = "message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    @Column("id")
    @NotNull
    private UUID id;

    @Column("chat")
    @NotNull(message = "Не указан чат для сообщения")
    private UUID chatId;

    @Column("author")
    @NotNull(message = "Не указан отправитель сообщения")
    private UUID authorId;

    @Column("timestamp")
    @NotNull(message = "врем метка")
    @CreatedDate
    private Timestamp timestamp;

    @Column("reply")
    private UUID replyId;
}
