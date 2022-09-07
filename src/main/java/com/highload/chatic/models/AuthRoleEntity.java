package com.highload.chatic.models;

import com.highload.chatic.security.AuthRole;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Entity
@Table (name = "authrole")
public class AuthRoleEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @Column(name = "name", unique = true)
    @NotEmpty(message = "Имя не должно быть пустым")
    @Enumerated(EnumType.STRING)
    private AuthRole name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AuthRole getName() {
        return name;
    }

    public void setName(AuthRole name) {
        this.name = name;
    }
}
