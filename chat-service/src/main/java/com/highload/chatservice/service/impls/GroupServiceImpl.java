package com.highload.chatservice.service.impls;

import com.highload.chatservice.client.PersonFeignClient;
import com.highload.chatservice.client.shared.PersonResponseDto;
import com.highload.chatservice.dto.PageResponseDto;
import com.highload.chatservice.dto.group.GroupMemberRequestDto;
import com.highload.chatservice.dto.group.GroupMemberResponseDto;
import com.highload.chatservice.dto.group.GroupRequestDto;
import com.highload.chatservice.dto.group.GroupResponseDto;
import com.highload.chatservice.exception.IllegalAccessException;
import com.highload.chatservice.exception.ResourceNotFoundException;
import com.highload.chatservice.models.*;
import com.highload.chatservice.repository.GroupMemberRepository;
import com.highload.chatservice.repository.GroupRoleRepository;
import com.highload.chatservice.repository.PGroupRepository;
import com.highload.chatservice.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupMemberRepository groupMemberRepository;
    private final PersonFeignClient personClient;
    private final PGroupRepository groupRepository;
    private final GroupRoleRepository groupRoleRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<GroupResponseDto> addGroup(String username, GroupRequestDto groupRequestDto) {
        Mono<PersonResponseDto> getPerson = personClient.getPerson(username);
        return Mono.zip(saveGroup(groupRequestDto), getPerson)
                .flatMap(tuple -> {
                    GroupMemberRequestDto groupMemberRequestDto = GroupMemberRequestDto.builder()
                            .personId(tuple.getT2().getId())
                            .writePosts(true)
                            .writeComments(true)
                            .manageMembers(true)
                            .build();
                    return createGroupMember(tuple.getT1().getId(), groupMemberRequestDto)
                            .thenReturn(tuple.getT1());
                })
                .map(it -> modelMapper.map(it, GroupResponseDto.class));
    }

    @Override
    public Mono<PageResponseDto<GroupMemberResponseDto>> getMembers(String username, UUID groupId, Pageable pageable) {
        Mono<Void> authorizeReadOperation =
                personClient.getPerson(username)
                        .flatMap(person ->
                                authorizeOperation(groupId, person.getId(), ChatOperation.READ));

        Mono<Page<GroupMember>> getAllGroupMembers =
                Mono.fromCallable(() -> groupMemberRepository.findAllByGroupId(groupId, pageable))
                        .subscribeOn(Schedulers.boundedElastic());

        return authorizeReadOperation.then(getAllGroupMembers)
                .map(page -> page.map(this::groupMemberToResponseDto))
                .map(PageResponseDto::new);
    }

    @Override
    public Mono<GroupMemberResponseDto> getMember(String username, UUID groupId, UUID personId) {
        Mono<Void> authorizeReadOperation =
                personClient.getPerson(username)
                        .flatMap(person ->
                                authorizeOperation(groupId, person.getId(), ChatOperation.READ));

        return authorizeReadOperation
                .then(getGroupMember(groupId, personId))
                .map(groupMember -> modelMapper.map(groupMember, GroupMemberResponseDto.class));
    }

    @Override
    public Mono<GroupMemberResponseDto> addMember(String username, UUID groupId, GroupMemberRequestDto groupMemberRequestDto) {
        Mono<Void> authorizeManageGroupOp =
                personClient.getPerson(username)
                        .flatMap(person ->
                                authorizeOperation(groupId, person.getId(), ChatOperation.MANAGE));

        Mono<GroupMemberResponseDto> createGroupMember =
                getMember(username, groupId, groupMemberRequestDto.getPersonId())
                        .onErrorResume(ResourceNotFoundException.class, e ->
                                createGroupMember(groupId, groupMemberRequestDto)
                                        .map(this::groupMemberToResponseDto));

        return authorizeManageGroupOp.then(createGroupMember);
    }

    @Override
    public Mono<Void> updateMember(String username, UUID groupId, GroupMemberRequestDto requestDto) {
        Mono<Void> authorizeManageGroupOp =
                personClient.getPerson(username)
                        .flatMap(person ->
                                authorizeOperation(groupId, person.getId(), ChatOperation.MANAGE));

        return authorizeManageGroupOp.then(getGroupMember(groupId, requestDto.getPersonId()))
                .zipWith(getGroupRole(requestDto))
                .map(tuple -> {
                    tuple.getT1().setRole(tuple.getT2());
                    return tuple.getT1();
                })
                .flatMap(this::saveGroupMember)
                .then();
    }

    @Override
    public Mono<Void> deleteMember(String username, UUID groupId, UUID personId) {
        Mono<Void> authorizeManageGroupOp =
                personClient.getPerson(username)
                        .flatMap(person ->
                                authorizeOperation(groupId, person.getId(), ChatOperation.MANAGE));

        Mono<Void> deleteMember =
                getGroupMember(groupId, personId)
                        .flatMap(groupMember -> Mono.fromCallable(() -> {
                            groupMemberRepository.deleteById(groupMember.getGroupMemberId());
                            return null;
                        }
                        ).subscribeOn(Schedulers.boundedElastic()))
                        .then();

        return authorizeManageGroupOp.then(deleteMember);
    }

    @Override
    public Mono<PageResponseDto<GroupResponseDto>> getAllGroups(String username, Pageable pageable) {
        return personClient.getPerson(username)
                .flatMap(person -> Mono.fromCallable(() -> {
                            Page<GroupMember> groupMemberGroupPage = groupMemberRepository.findAllByUserId(person.getId(), pageable);
                            Collection<PGroup> groups = groupRepository.findAllByIdIn(
                                    groupMemberGroupPage.map(member -> member.getGroupMemberId().getPgroupId())
                                            .toList()
                            );
                            return new PageResponseDto<>(
                                    groups.stream().map(group -> modelMapper.map(group, GroupResponseDto.class)).toList(),
                                    groupMemberGroupPage
                            );
                        })
                        .subscribeOn(Schedulers.boundedElastic())
                );
    }

    @Override
    public Mono<Void> authorizeOperation(UUID groupId, UUID personId, ChatOperation operation) {
        return getGroupMember(groupId, personId)
                .filter(groupMember ->
                        switch (operation) {
                            case WRITE -> groupMember.getRole().isWritePosts();
                            case REPLY -> groupMember.getRole().isWriteComments();
                            case MANAGE -> groupMember.getRole().isManageMembers();
                            case READ -> true;
                        }
                )
                .switchIfEmpty(Mono.error(IllegalAccessException::new))
                .then();
    }

    private Mono<GroupMember> getGroupMember(UUID groupId, UUID personId) {
        return Mono.fromCallable(() -> {
                    GroupMemberId groupMemberId = GroupMemberId.builder()
                            .personId(personId)
                            .pgroupId(groupId)
                            .build();
                    return groupMemberRepository.findByGroupMemberId(groupMemberId)
                            .orElseThrow(ResourceNotFoundException::new);
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<GroupMember> createGroupMember(UUID groupId, GroupMemberRequestDto requestDto) {
        return getGroupRole(requestDto).flatMap(role -> {
            GroupMemberId memberId = GroupMemberId.builder()
                    .personId(requestDto.getPersonId())
                    .pgroupId(groupId)
                    .build();

            GroupMember newGroupMember = GroupMember.builder()
                    .groupMemberId(memberId)
                    .role(role)
                    .build();

            return saveGroupMember(newGroupMember);
        });
    }

    private Mono<PGroup> saveGroup(GroupRequestDto groupRequestDto) {
        return Mono.fromCallable(() -> {
                    PGroup group = new PGroup();
                    group.setId(groupRequestDto.getId());
                    group.setName(groupRequestDto.getName());
                    return groupRepository.save(group);
                }
        ).subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<GroupMember> saveGroupMember(GroupMember groupMember) {
        return Mono.fromCallable(() -> groupMemberRepository.save(groupMember))
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<GroupRole> getGroupRole(GroupMemberRequestDto requestDto) {
        return Mono.fromCallable(() -> groupRoleRepository.
                findByWritePostsAndWriteCommentsAndManageMembers(requestDto.isWritePosts(),
                        requestDto.isWriteComments(),
                        requestDto.isManageMembers())
                .orElseGet(() -> groupRoleRepository.save(GroupRole
                        .builder()
                        .writePosts(requestDto.isWritePosts())
                        .writeComments(requestDto.isWriteComments())
                        .manageMembers(requestDto.isManageMembers())
                        .build())))
                .subscribeOn(Schedulers.boundedElastic());
    }

    private GroupMemberResponseDto groupMemberToResponseDto(GroupMember groupMember) {
        return GroupMemberResponseDto.builder()
                .groupId(groupMember.getGroupMemberId().getPgroupId())
                .personId(groupMember.getGroupMemberId().getPersonId())
                .writeComments(groupMember.getRole().isWriteComments())
                .writePosts(groupMember.getRole().isWritePosts())
                .manageMembers(groupMember.getRole().isManageMembers())
                .build();
    }
}
