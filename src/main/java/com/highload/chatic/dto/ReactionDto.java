package com.highload.chatic.dto;

import com.highload.chatic.models.Message;
import com.highload.chatic.models.Person;
import com.highload.chatic.models.ReactionId;

import javax.persistence.EmbeddedId;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ReactionDto {
    @EmbeddedId
    private ReactionId reactionId;

    @Size(max = 50, message = "Поле должно быть до 50 символов длиной")
    @NotEmpty(message = "Нет реакции")
    private String emoji;

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
