package com.highload.chatservice.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pgroup", schema = "public", catalog = "chatic")
@PrimaryKeyJoinColumn(name = "id")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PGroup extends Chat {

    @Column(name = "name")
    @NotNull
    private String name;

}
