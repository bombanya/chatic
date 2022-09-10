package com.highload.chatic.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "reaction", schema = "public", catalog = "chatic")
@Getter
@Setter
public class Reaction {
    @EmbeddedId
    private ReactionId reactionId;

    @Column(name = "emoji")
    @Enumerated(EnumType.STRING)
    private Emoji emoji;

    public Reaction(UUID messageId, UUID personId, Emoji emoji) {
        this.reactionId = new ReactionId(messageId, personId);
        this.emoji = emoji;
    }

    protected Reaction() {
    }

    /*public Message getMessage() {
        return reactionId.getMessage();
    }

    public void setMessage(Message message) {
        reactionId.setMessage(message);
    }

    public Person getPerson() {
        return reactionId.getPerson();
    }

    public void setPerson(Person person) {
        reactionId.setPerson(person);
    }*/
}
