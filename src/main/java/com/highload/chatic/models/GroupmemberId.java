package com.highload.chatic.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GroupmemberId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "pgroup")
    private Pgroup pgroup;

    @ManyToOne
    @JoinColumn(name = "person")
    private Person person;

    public Pgroup getPgroup() {
        return pgroup;
    }

    public void setPgroup(Pgroup pgroup) {
        this.pgroup = pgroup;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
