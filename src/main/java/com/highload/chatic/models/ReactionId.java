package com.highload.chatic.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReactionId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "message", insertable = false, updatable = false)
    private Message message;

    @ManyToOne
    @JoinColumn(name = "message", insertable = false, updatable = false)
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
