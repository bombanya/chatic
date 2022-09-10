package com.highload.chatic.dto.person;

import com.highload.chatic.dto.validation.AddRequest;
import com.highload.chatic.dto.validation.UpdateRequest;
import com.highload.chatic.models.AuthRoleName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
public class PersonRequestDto {

    @Null(groups = AddRequest.class)
    @NotNull(groups = UpdateRequest.class)
    private UUID id;

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
