package com.highload.chatic.models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "reaction", schema = "public", catalog = "chatic")
public class Reaction {
    @EmbeddedId
    private ReactionId reactionId;

    @Column(name = "emoji")
    @Size(max = 50, message = "Поле должно быть до 50 символов длиной")
    @NotEmpty(message = "Нет реакции")
    private String emoji;

    public Reaction(UUID messageId, UUID personId, String emoji) {
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

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }
}
