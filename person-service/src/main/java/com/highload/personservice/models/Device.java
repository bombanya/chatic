package com.highload.personservice.models;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
public class Device {

    private UUID id;

    @NotNull(message = "Необходимо указание владельца")
    private UUID personId;

    @NotEmpty(message = "Нет указания места")
    private String geo;

    @NotEmpty(message = "Нет указания mac-адреса")
    private String mac;
}
