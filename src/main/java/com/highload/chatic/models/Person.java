package com.highload.chatic.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "person", schema = "public", catalog = "chatic")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @Column(name = "username", unique = true)
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 20, message = "Имя должно быть от 2 до 20 символов длиной")
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "Пароль не должен быть пустым")
    @Size(max = 20, message = "Пароль не должен превышать 20 символов")
    private String password;

    @Column(name = "bio")
    @Size(max = 70, message = "Поле должно быть до 70 символов длиной")
    private String bio;

    @OneToMany(mappedBy = "person")
    private List<Device> devices;

    @OneToMany(mappedBy = "author")
    private List<Message> messages;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person that = (Person) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(bio, that.bio);
    }
}
