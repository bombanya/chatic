package com.highload.chatic.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReactionId implements Serializable {

    @Column(name = "message")
    private UUID messageId;
    @Column(name = "person")
    private UUID personId;

}
