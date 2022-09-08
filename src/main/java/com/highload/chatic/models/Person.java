package com.highload.chatic.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
    private String password;

    @Column(name = "bio")
    @Size(max = 70, message = "Поле должно быть до 70 символов длиной")
    private String bio;

    @Column(name = "auth_role")
    @Enumerated(EnumType.STRING)
    private AuthRoleName authRole;

//    @OneToMany(mappedBy = "person")
//    private List<Device> devices;
    public Person(String username, String password, String bio) {
        this.username = username;
        this.password = password;
        this.bio = bio;
    }

    protected Person() {
    }

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

    public AuthRoleName getAuthRole() {
        return authRole;
    }

    public void setAuthRole(AuthRoleName authRole) {
        this.authRole = authRole;
    }

//    public List<Device> getDevices() {
//        return devices;
//    }

//    public void setDevices(List<Device> devices) {
//        this.devices = devices;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person that = (Person) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(bio, that.bio);
    }
}
