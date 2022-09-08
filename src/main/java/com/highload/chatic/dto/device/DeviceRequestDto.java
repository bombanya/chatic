package com.highload.chatic.dto.device;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

public record DeviceRequestDto(
        @NotEmpty(message = "Необходимо указание владельца") UUID personId,
        @NotEmpty(message = "Нет указания места") String geo,
        @NotEmpty(message = "Нет указания mac-адреса") String mac
) {
}
