package com.highload.chatic.models;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "pgroup", schema = "public", catalog = "chatic")
@PrimaryKeyJoinColumn(name = "id")
public class Pgroup extends Chat{



}
