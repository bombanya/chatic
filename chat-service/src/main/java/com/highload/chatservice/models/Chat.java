package com.highload.chatservice.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "chat")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class Chat {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;
}
