package com.highload.chatservice.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "personalchat")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonalChat extends Chat {

    @Column(name = "person1")
    @NotNull(message = "Не указан участник (1) чата")
    private UUID person1Id;

    @Column(name = "person2")
    @NotNull(message = "Не указан участник (2) чата")
    private UUID person2Id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PersonalChat that = (PersonalChat) o;
        return Objects.equals(person1Id, that.person1Id) && Objects.equals(person2Id, that.person2Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), person1Id, person2Id);
    }
}
