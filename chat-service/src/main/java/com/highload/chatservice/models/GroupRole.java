package com.highload.chatservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "grouprole")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
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

}
