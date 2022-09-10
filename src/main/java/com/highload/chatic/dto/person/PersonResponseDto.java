package com.highload.chatic.dto.person;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PersonResponseDto {
    private UUID id;
    private String username;
    private String bio;
}
