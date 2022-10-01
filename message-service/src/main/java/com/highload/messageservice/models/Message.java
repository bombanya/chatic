package com.highload.messageservice.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "message", schema = "public", catalog = "chatic")
public class Message {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

//    @ManyToOne
//    @JoinColumn(name = "chat", referencedColumnName = "id")
//    @NotEmpty(message = "Не указан чат для сообщения")
//    private Chat chat;

    @Column(name = "chat")
    @NotNull(message = "Не указан чат для сообщения")
    private UUID chatId;

//    @ManyToOne
//    @JoinColumn(name = "author", referencedColumnName = "id")
//    private Person author;

    @Column(name = "author")
    @NotNull(message = "Не указан отправитель сообщения")
    private UUID authorId;

    @CreationTimestamp
    @Column(name = "timestamp")
    @NotNull(message = "врем метка")
    private Timestamp timestamp;

//    @ManyToOne
//    @JoinColumn(name = "reply", referencedColumnName = "id")
//    private Message reply;

    @Column(name = "reply")
    private UUID replyId;

    public Message(UUID chatId, UUID authorId, UUID replyId) {
        this.timestamp = Timestamp.from(Instant.now());
        this.chatId = chatId;
        this.authorId = authorId;
        this.replyId = replyId;
    }

    protected Message() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    /*public Chat getChat() {
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
    }*/

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /*public Message getReply() {
        return reply;
    }

    public void setReply(Message reply) {
        this.reply = reply;
    }*/

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public UUID getReplyId() {
        return replyId;
    }

    public void setReplyId(UUID replyId) {
        this.replyId = replyId;
    }
}
