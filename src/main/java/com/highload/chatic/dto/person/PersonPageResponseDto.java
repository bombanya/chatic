package com.highload.chatic.dto.person;

import com.highload.chatic.dto.PageResponseDto;

import java.util.List;

public class PersonPageResponseDto extends PageResponseDto<PersonResponseDto> {
    public PersonPageResponseDto(List<PersonResponseDto> page, boolean hasNext) {
        super(page, hasNext);
    }
}
