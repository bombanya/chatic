package com.highload.chatic.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Entity
@Table(name = "device", schema = "public", catalog = "chatic")
public class Device {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

//    @ManyToOne
//    @JoinColumn(name = "person", referencedColumnName = "id")
//    @NotEmpty(message = "Необходимо указание владельца")
//    private Person person;

    @Column(name = "person")
    @NotEmpty(message = "Необходимо указание владельца")
    private UUID personId;

    @Column(name = "geo")
    @NotEmpty(message = "Нет указания места")
    private String geo;

    @Column(name = "mac")
    @NotEmpty(message = "Нет указания mac-адреса")
    private String mac;

    public Device(UUID personId, String geo, String mac) {
        this.personId = personId;
        this.geo = geo;
        this.mac = mac;
    }

    protected Device() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

//    public Person getPerson() {
//        return person;
//    }

//    public void setPerson(Person person) {
//        this.person = person;
//    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }
}
