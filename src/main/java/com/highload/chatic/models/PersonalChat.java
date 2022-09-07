package com.highload.chatic.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "personalchat", schema = "public", catalog = "chatic")
@PrimaryKeyJoinColumn(name = "id")
public class PersonalChat extends Chat {
    @ManyToOne
    @JoinColumn(name = "person1", referencedColumnName = "id")
    @NotEmpty(message = "Не указан участник (1) чата")
    private Person person1;

    @ManyToOne
    @JoinColumn(name = "person2", referencedColumnName = "id")
    @NotEmpty(message = "Не указан участник (2) чата")
    private Person person2;


    public Person getPerson1() {
        return person1;
    }

    public void setPerson1(Person person1) {
        this.person1 = person1;
    }

    public Person getPerson2() {
        return person2;
    }

    public void setPerson2(Person person2) {
        this.person2 = person2;
    }
}
