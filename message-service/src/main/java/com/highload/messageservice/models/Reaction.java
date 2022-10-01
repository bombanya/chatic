package com.highload.messageservice.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "Должна быть задана реакция")
    private Emoji emoji;

    public Reaction(UUID messageId, UUID personId, Emoji emoji) {
        this.reactionId = new ReactionId(messageId, personId);
        this.emoji = emoji;
    }

    protected Reaction() {
    }
}
