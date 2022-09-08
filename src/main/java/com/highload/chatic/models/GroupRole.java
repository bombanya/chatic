package com.highload.chatic.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "grouprole", schema = "public", catalog = "chatic")
public class GroupRole {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @Column(name = "write_posts")
    private boolean writePosts;

    @Column(name = "write_comments")
    private boolean writeComments;

    @Column(name = "manage_members")
    private boolean manageMembers;

    public GroupRole(boolean writePosts, boolean writeComments, boolean manageMembers) {
        this.writePosts = writePosts;
        this.writeComments = writeComments;
        this.manageMembers = manageMembers;
    }

    protected GroupRole() {
    }

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
