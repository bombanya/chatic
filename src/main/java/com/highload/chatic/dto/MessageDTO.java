package com.highload.chatic.dto;

import com.highload.chatic.models.Chat;
import com.highload.chatic.models.Message;

import javax.validation.constraints.NotEmpty;


public class MessageDTO {
    @NotEmpty(message = "Не указан чат для сообщения")
    private Chat chat;

    private Message reply; //???

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Message getReply() {
        return reply;
    }

    public void setReply(Message reply) {
        this.reply = reply;
    }
}
