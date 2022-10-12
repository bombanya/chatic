package com.highload.chatservice.dto.group;

import com.highload.chatservice.dto.validation.AddRequest;
import com.highload.chatservice.dto.validation.UpdateRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.UUID;
@Data
public class GroupRequestDto {

    @Null(groups = AddRequest.class)
    @NotNull(groups = UpdateRequest.class)
    private final UUID id;

    @NotNull
    private final String name;

}
