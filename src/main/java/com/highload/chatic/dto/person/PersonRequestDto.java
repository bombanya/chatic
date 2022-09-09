package com.highload.chatic.dto.person;

import com.highload.chatic.models.AuthRoleName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PersonRequestDto {
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 20, message = "Имя должно быть от 2 до 20 символов длиной")
    private String username;

    @NotEmpty(message = "Пароль не должен быть пустым")
    private String password;

    @Size(max = 70, message = "Поле должно быть до 70 символов длиной")
    private String bio;

    @NotNull(message = "Должна быть указна роль")
    private AuthRoleName authRole;
}
