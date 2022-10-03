package com.highload.personservice.dto.device;

import java.util.UUID;

public record DeviceResponseDto(
        UUID id,
        UUID personId,
        String geo,
        String mac
) {
}
