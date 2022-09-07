package com.highload.chatic.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "message", schema = "public", catalog = "chatic")
public class Message {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "chat", referencedColumnName = "id")
    @NotEmpty(message = "Не указан чат для сообщения")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "id")
    @NotEmpty(message = "Не указан отправитель сообщения")
    private Person author;

    @CreationTimestamp
    @Column(name = "timestamp")
    @NotEmpty(message = "врем метка")
    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "reply", referencedColumnName = "id")
    private Message reply;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Message getReply() {
        return reply;
    }

    public void setReply(Message reply) {
        this.reply = reply;
    }
}
