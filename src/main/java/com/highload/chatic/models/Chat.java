package com.highload.chatic.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "chat", schema = "public", catalog = "chatic")
@Inheritance(strategy = InheritanceType.JOINED)
public class Chat {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
