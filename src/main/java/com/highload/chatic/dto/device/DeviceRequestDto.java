package com.highload.chatic.dto.device;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public record DeviceRequestDto(
        @NotNull(message = "Необходимо указание владельца") UUID personId,
        @NotEmpty(message = "Нет указания места") String geo,
        @NotEmpty(message = "Нет указания mac-адреса") String mac
) {
}
