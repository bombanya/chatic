package com.highload.chatic.dto.reaction;

import com.highload.chatic.models.Emoji;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReactionRequestDto {

    @NotNull(message = "Должна быть задана реакция")
    private Emoji emoji;
}
