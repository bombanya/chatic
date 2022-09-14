package com.highload.chatic.rest.controller;

import com.highload.chatic.dto.GroupRoleDto;
import com.highload.chatic.dto.PageResponseDto;
import com.highload.chatic.dto.group.GroupMemberResponseDto;
import com.highload.chatic.models.GroupRole;
import com.highload.chatic.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("groups")
public class GroupController {

    private final GroupService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addGroup(
            Principal principal
    ) {
        service.addGroup(principal.getName());
    }

    @GetMapping("{groupId}/members")
    @ResponseStatus(HttpStatus.OK)
    public PageResponseDto<GroupMemberResponseDto> getMembers (
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @PathVariable UUID groupId,
            Principal principal
    ) {
        return service.getMembers(principal.getName(), groupId, PageRequest.of(page, size));
    }

    @GetMapping("{groupId}/members/{personId}")
    @ResponseStatus(HttpStatus.OK)
    public GroupMemberResponseDto getMember (
            @PathVariable UUID groupId,
            @PathVariable UUID personId,
            Principal principal
    ) {
        return service.getMember(principal.getName(), groupId, personId);
    }

    @PostMapping("{groupId}/members/{personId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMember(
            @RequestBody GroupRoleDto groupRoleDto,
            @PathVariable UUID groupId,
            @PathVariable UUID personId,
            Principal principal
    ) {
        service.addMember(principal.getName(), groupId, personId, groupRoleDto);
    }

    @DeleteMapping("{groupId}/members/{personId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMember(
            @PathVariable UUID groupId,
            @PathVariable UUID personId,
            Principal principal
    ) {
        service.deleteMember(principal.getName(), groupId, personId);
    }
}
