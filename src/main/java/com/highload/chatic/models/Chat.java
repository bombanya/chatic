package com.highload.chatic.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "chat", schema = "public", catalog = "chatic")
@Inheritance(strategy = InheritanceType.JOINED)
public class Chat {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

//    @OneToMany(mappedBy = "chat")
//    private List<Message> messages;

    public Chat(UUID id) {
        this.id = id;
    }

    protected Chat() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    /*public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }*/
}
