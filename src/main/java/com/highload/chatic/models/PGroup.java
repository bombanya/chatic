package com.highload.chatic.models;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "pgroup", schema = "public", catalog = "chatic")
@PrimaryKeyJoinColumn(name = "id")
public class PGroup extends Chat {
}
