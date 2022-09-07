package com.highload.chatic.models;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class GroupMemberId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "pgroup")
    private PGroup pgroup;

    @ManyToOne
    @JoinColumn(name = "person")
    private Person person;

    public PGroup getPgroup() {
        return pgroup;
    }

    public void setPgroup(PGroup pgroup) {
        this.pgroup = pgroup;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
