package com.highload.chatic.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReactionId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "message")
    private Message message;

    @ManyToOne
    @JoinColumn(name = "person")
    private Person person;

    public Message getMessage() {
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
    }
}
