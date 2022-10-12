package com.highload.chatservice.rest.controller;

import com.highload.chatservice.dto.PageResponseDto;
import com.highload.chatservice.dto.group.GroupMemberRequestDto;
import com.highload.chatservice.dto.group.GroupMemberResponseDto;
import com.highload.chatservice.dto.group.GroupRequestDto;
import com.highload.chatservice.dto.group.GroupResponseDto;
import com.highload.chatservice.dto.validation.AddRequest;
import com.highload.chatservice.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
@Validated
public class GroupController {

    private final GroupService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(AddRequest.class)
    public Mono<GroupResponseDto> addGroup(@RequestHeader("USERNAME") String username,
                                           @RequestBody @Valid GroupRequestDto groupRequestDto) {
        return service.addGroup(username, groupRequestDto);
    }

    @GetMapping("/{groupId}/members")
    @ResponseStatus(HttpStatus.OK)
    public Mono<PageResponseDto<GroupMemberResponseDto>> getMembers(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size,
                                                                    @PathVariable UUID groupId,
                                                                    @RequestHeader("USERNAME") String username) {
        return service.getMembers(username, groupId, PageRequest.of(page, size));
    }

    @GetMapping("/{groupId}/members/{personId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<GroupMemberResponseDto> getMember(@PathVariable UUID groupId,
                                                  @PathVariable UUID personId,
                                                  @RequestHeader("USERNAME") String username) {
        return service.getMember(username, groupId, personId);
    }

    @PostMapping("/{groupId}/members")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<GroupMemberResponseDto> addMember(@RequestBody @Valid GroupMemberRequestDto groupMemberRequestDto,
                                                  @PathVariable UUID groupId,
                                                  @RequestHeader("USERNAME") String username) {
        return service.addMember(username, groupId, groupMemberRequestDto);
    }

    @PutMapping("/{groupId}/members")
    @ResponseStatus(HttpStatus.OK)
    public Mono<GroupMemberResponseDto> updateMember(@RequestBody @Valid GroupMemberRequestDto groupMemberRequestDto,
                                                     @PathVariable UUID groupId,
                                                     @RequestHeader("USERNAME") String username) {
        return service.addMember(username, groupId, groupMemberRequestDto);
    }

    @DeleteMapping("/{groupId}/members/{personId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteMember(@PathVariable UUID groupId,
                                   @PathVariable UUID personId,
                                   @RequestHeader("USERNAME") String username) {
        return service.deleteMember(username, groupId, personId);
    }
}