package com.highload.chatic.dto.person;

import com.highload.chatic.dto.device.DeviceRequestDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record PersonRequestDto(
        @NotEmpty(message = "Имя не должно быть пустым")
        @Size(min = 2, max = 20, message = "Имя должно быть от 2 до 20 символов длиной")
        String username,

        @NotEmpty(message = "Пароль не должен быть пустым")
        @Size(max = 20, message = "Пароль не должен превышать 20 символов")
        String password,

        @Size(max = 70, message = "Поле должно быть до 70 символов длиной")
        String bio,

        @NotNull(message = "Необходимо указание владельца")
        DeviceRequestDto device
) {
}
