package com.highload.chatic.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class GroupMemberId implements Serializable {
//    @ManyToOne
//    @JoinColumn(name = "pgroup")
//    private PGroup pgroup;

    @Column(name = "pgroup")
    private UUID pgroupId;

//    @ManyToOne
//    @JoinColumn(name = "person")
//    private Person person;

    @Column(name = "person")
    private UUID personId;

    public GroupMemberId(UUID pgroupId, UUID personId) {
        this.pgroupId = pgroupId;
        this.personId = personId;
    }

    protected GroupMemberId() {
    }

//    public PGroup getPgroup() {
//        return pgroup;
//    }

//    public void setPgroup(PGroup pgroup) {
//        this.pgroup = pgroup;
//    }

//    public Person getPerson() {
//        return person;
//    }

//    public void setPerson(Person person) {
//        this.person = person;
//    }

    public UUID getPgroupId() {
        return pgroupId;
    }

    public void setPgroupId(UUID pgroupId) {
        this.pgroupId = pgroupId;
    }

    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }
}
