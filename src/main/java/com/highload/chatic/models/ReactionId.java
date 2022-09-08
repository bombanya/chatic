package com.highload.chatic.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class ReactionId implements Serializable {
//    @ManyToOne
//    @JoinColumn(name = "message")
//    private Message message;

    @Column(name = "message")
    private UUID messageId;

//    @ManyToOne
//    @JoinColumn(name = "person")
//    private Person person;

    @Column(name = "person")
    private UUID personId;

    public ReactionId(UUID messageId, UUID personId) {
        this.messageId = messageId;
        this.personId = personId;
    }

    protected ReactionId() {
    }

    /*public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }*/

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }
}
