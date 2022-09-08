package com.highload.chatic.dto.device;

import java.util.UUID;

public record DeviceResponseDto(
        UUID id,
        UUID personId,
        String geo,
        String mac
) {
}
