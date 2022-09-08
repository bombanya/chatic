package com.highload.chatic.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "authrole")
public class AuthRole {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @Column(name = "name", unique = true)
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthRoleName name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AuthRoleName getName() {
        return name;
    }

    public void setName(AuthRoleName name) {
        this.name = name;
    }

}
