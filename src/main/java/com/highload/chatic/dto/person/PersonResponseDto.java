package com.highload.chatic.dto.person;

import com.highload.chatic.dto.device.DeviceResponseDto;

import java.util.List;
import java.util.UUID;

public record PersonResponseDto(
        UUID id,
        String username,
        String bio
) {
}
