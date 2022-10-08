package com.highload.personservice.dto.person;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonAuthDto {

    private String username;
    private String password;
    private String authRole;
}
