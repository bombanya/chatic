package com.highload.chatservice.dto.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class GroupResponseDto {
    private UUID id;
    private String name;
}
