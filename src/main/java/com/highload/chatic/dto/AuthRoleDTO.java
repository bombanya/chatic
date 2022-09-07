package com.highload.chatic.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AuthRoleDTO {
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 20, message = "Название роли должно быть от 2 до 20 символов длиной")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
