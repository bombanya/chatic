package com.highload.chatic.dto;

import com.highload.chatic.models.Person;

import javax.validation.constraints.NotEmpty;

public class PersonalchatDTO {
    @NotEmpty(message = "Не указан участник (2) чата")
    private Person person2;

    public Person getPerson2() {
        return person2;
    }

    public void setPerson2(Person person2) {
        this.person2 = person2;
    }
}
