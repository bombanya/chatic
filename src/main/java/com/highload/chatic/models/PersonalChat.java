package com.highload.chatic.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Entity
@Table(name = "personalchat", schema = "public", catalog = "chatic")
@PrimaryKeyJoinColumn(name = "id")
public class PersonalChat extends Chat {
//    @ManyToOne
//    @JoinColumn(name = "person1", referencedColumnName = "id")
//    @NotEmpty(message = "Не указан участник (1) чата")
//    private Person person1;

    @Column(name = "person1")
    @NotEmpty(message = "Не указан участник (1) чата")
    private UUID person1Id;

//    @ManyToOne
//    @JoinColumn(name = "person2", referencedColumnName = "id")
//    @NotEmpty(message = "Не указан участник (2) чата")
//    private Person person2;

    @Column(name = "person2")
    @NotEmpty(message = "Не указан участник (2) чата")
    private UUID person2Id;

    public PersonalChat(UUID person1Id, UUID person2Id) {
        this.person1Id = person1Id;
        this.person2Id = person2Id;
    }

    public PersonalChat() {
    }

//    public Person getPerson1() {
//        return person1;
//    }

//    public void setPerson1(Person person1) {
//        this.person1 = person1;
//    }

//    public Person getPerson2() {
//        return person2;
//    }

//    public void setPerson2(Person person2) {
//        this.person2 = person2;
//    }

    public UUID getPerson1Id() {
        return person1Id;
    }

    public void setPerson1Id(UUID person1Id) {
        this.person1Id = person1Id;
    }

    public UUID getPerson2Id() {
        return person2Id;
    }

    public void setPerson2Id(UUID person2Id) {
        this.person2Id = person2Id;
    }
}
