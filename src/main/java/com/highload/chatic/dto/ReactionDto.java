package com.highload.chatic.dto;

import com.highload.chatic.models.Emoji;


public class ReactionDto {

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
