package com.highload.chatic.dto.personalchat;

import com.highload.chatic.dto.PageResponseDto;

import java.util.List;

public class PersonalChatPageResponseDto extends PageResponseDto<PersonalChatResponseDto> {
    public PersonalChatPageResponseDto(List<PersonalChatResponseDto> page, boolean hasNext) {
        super(page, hasNext);
    }
}
