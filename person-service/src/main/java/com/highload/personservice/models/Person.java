package com.highload.personservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private UUID id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 20, message = "Имя должно быть от 2 до 20 символов длиной")
    private String username;

    @NotEmpty(message = "Пароль не должен быть пустым")
    private String password;

    @Size(max = 70, message = "Поле должно быть до 70 символов длиной")
    private String bio;

    @NotNull
    private AuthRoleName authRole;

    private boolean deleted;
}
