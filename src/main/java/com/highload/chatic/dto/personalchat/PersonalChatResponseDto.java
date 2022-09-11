package com.highload.chatic.dto.personalchat;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PersonalChatResponseDto {
    private UUID id;
    private UUID person1Id;
    private UUID person2Id;
}
