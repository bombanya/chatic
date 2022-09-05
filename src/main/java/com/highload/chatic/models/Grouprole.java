package com.highload.chatic.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "grouprole", schema = "public", catalog = "chatic")
public class Grouprole {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "write_posts")
    @NotEmpty(message = "Укажите права")
    private boolean writePosts;

    @Column(name = "write_comments")
    @NotEmpty(message = "Укажите права")
    private boolean writeComments;

    @Column(name = "manage_members")
    @NotEmpty(message = "Укажите права")
    private boolean manageMembers;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isWritePosts() {
        return writePosts;
    }

    public void setWritePosts(boolean writePosts) {
        this.writePosts = writePosts;
    }

    public boolean isWriteComments() {
        return writeComments;
    }

    public void setWriteComments(boolean writeComments) {
        this.writeComments = writeComments;
    }

    public boolean isManageMembers() {
        return manageMembers;
    }

    public void setManageMembers(boolean manageMembers) {
        this.manageMembers = manageMembers;
    }
}
