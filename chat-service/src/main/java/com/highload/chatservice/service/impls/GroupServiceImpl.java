package com.highload.chatservice.service.impls;

import com.highload.chatservice.client.PersonFeignClient;
import com.highload.chatservice.client.shared.PersonResponseDto;
import com.highload.chatservice.dto.PageResponseDto;
import com.highload.chatservice.dto.group.GroupMemberRequestDto;
import com.highload.chatservice.dto.group.GroupMemberResponseDto;
import com.highload.chatservice.dto.group.GroupRequestDto;
import com.highload.chatservice.dto.group.GroupResponseDto;
import com.highload.chatservice.exception.InvalidRequestException;
import com.highload.chatservice.exception.ResourceNotFoundException;
import com.highload.chatservice.models.*;
import com.highload.chatservice.repository.GroupMemberRepository;
import com.highload.chatservice.repository.GroupRoleRepository;
import com.highload.chatservice.repository.PGroupRepository;
import com.highload.chatservice.service.ChatService;
import com.highload.chatservice.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupMemberRepository groupMemberRepository;
    private final PersonFeignClient personClient;
    private final ChatService chatService;
    private final PGroupRepository groupRepository;
    private final GroupRoleRepository groupRoleRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<GroupMemberResponseDto> getGroupMemberInfo(UUID group, UUID personId) {
        GroupMemberId groupMemberId = GroupMemberId.builder()
                .personId(personId)
                .pgroupId(group)
                .build();
        return Mono.fromCallable(() -> groupMemberRepository.findByGroupMemberId(groupMemberId)
                        .orElseThrow(ResourceNotFoundException::new)
                ).subscribeOn(Schedulers.boundedElastic())
                .map(groupMember -> modelMapper.map(groupMember, GroupMemberResponseDto.class));
    }

    @Override
    public Mono<GroupResponseDto> addGroup(String username, GroupRequestDto groupRequestDto) {
        Mono<PersonResponseDto> getPerson = personClient.getPerson(username);
        GroupRole adminRole = GroupRole.builder()
                .manageMembers(true)
                .writeComments(true)
                .writePosts(true)
                .build();
        return Mono.zip(saveGroup(groupRequestDto), getPerson)
                .flatMap(tuple -> {
                            GroupMemberId memberId = GroupMemberId.builder()
                                    .personId(tuple.getT2().getId())
                                    .pgroupId(tuple.getT1().getId())
                                    .build();
                            GroupMember groupMember = GroupMember.builder()
                                    .groupMemberId(memberId)
                                    .role(adminRole)
                                    .build();
                            return saveGroupMember(groupMember).thenReturn(tuple.getT1());
                        }
                ).map(it -> modelMapper.map(it, GroupResponseDto.class));
    }

    @Override
    public Mono<PageResponseDto<GroupMemberResponseDto>> getMembers(String username, UUID groupId, Pageable pageable) {
        Mono<Void> authorizeReadOperation =
                chatService.authorizeOperation(groupId, username, ChatOperation.READ);

        Mono<Page<GroupMember>> getAllGroupMembers =
                Mono.fromCallable(() -> groupMemberRepository.findAllByGroupId(groupId))
                        .subscribeOn(Schedulers.boundedElastic());

        return authorizeReadOperation.then(getAllGroupMembers)
                .map(page -> page.map(this::groupMemberToResponseDto))
                .map(PageResponseDto::new);
    }

    @Override
    public Mono<GroupMemberResponseDto> getMember(String username, UUID groupId, UUID personId) {
        Mono<Void> authorizeReadOperation =
                chatService.authorizeOperation(groupId, username, ChatOperation.READ);

        GroupMemberId groupMemberId =
                GroupMemberId.builder()
                        .personId(personId)
                        .pgroupId(groupId)
                        .build();

        Mono<GroupMember> getGroupMember =
                Mono.fromCallable(() ->
                        groupMemberRepository.findByGroupMemberId(groupMemberId)
                                .orElseThrow(ResourceNotFoundException::new)
                ).subscribeOn(Schedulers.boundedElastic());

        return authorizeReadOperation.then(getGroupMember)
                .map(this::groupMemberToResponseDto);
    }

    @Override
    public Mono<GroupMemberResponseDto> addMember(String username, UUID groupId, GroupMemberRequestDto groupMemberRequestDto) {
        Mono<Void> authorizeManageGroupOp =
                chatService.authorizeOperation(groupId, username, ChatOperation.MANAGE);

        Mono<GroupMemberResponseDto> createGroupMember =
                getMember(username, groupId, groupMemberRequestDto.getPersonId())
                        .switchIfEmpty(createGroupMember(groupId, groupMemberRequestDto)
                                .map(this::groupMemberToResponseDto)
                        ).then(Mono.error(InvalidRequestException::new));

        return authorizeManageGroupOp.then(createGroupMember);
    }

    @Override
    public Mono<GroupMemberResponseDto> updateMember(String username, UUID groupId, GroupMemberRequestDto requestDto) {
        Mono<Void> authorizeManageGroupOp =
                chatService.authorizeOperation(groupId, username, ChatOperation.MANAGE);

        return authorizeManageGroupOp.then(
                        Mono.fromCallable(() -> {
                                    GroupMemberId memberId = GroupMemberId.builder()
                                            .personId(requestDto.getPersonId())
                                            .pgroupId(groupId)
                                            .build();

                                    GroupMember groupMember = groupMemberRepository.findById(memberId).orElseThrow(ResourceNotFoundException::new);

                                    groupMember.setRole(GroupRole.builder()
                                            .id(groupMember.getRole().getId())
                                            .manageMembers(requestDto.isManageMembers())
                                            .writePosts(requestDto.isWritePosts())
                                            .writeComments(requestDto.isWriteComments())
                                            .build());

                                    return groupMember;
                                }
                        ).subscribeOn(Schedulers.boundedElastic())
                ).flatMap(this::saveGroupMember)
                .map(this::groupMemberToResponseDto);
    }

    @Override
    public Mono<Void> deleteMember(String username, UUID groupId, UUID personId) {
        Mono<Void> authorizeManageGroupOp =
                chatService.authorizeOperation(groupId, username, ChatOperation.MANAGE);

        Mono<Void> deleteMember =
                Mono.fromCallable(() -> {
                                    GroupMemberId id = new GroupMemberId(groupId, personId);
                                    if (!groupMemberRepository.existsById(new GroupMemberId(groupId, personId)))
                                        throw new ResourceNotFoundException();
                                    groupMemberRepository.deleteById(id);
                                    return null;
                                }
                        ).subscribeOn(Schedulers.boundedElastic())
                        .then();

        return authorizeManageGroupOp.then(deleteMember);
    }

    private Mono<GroupMember> createGroupMember(UUID groupId, GroupMemberRequestDto requestDto) {
        GroupRole newRole = GroupRole.builder()
                .manageMembers(requestDto.isManageMembers())
                .writePosts(requestDto.isWritePosts())
                .writeComments(requestDto.isWriteComments())
                .build();

        GroupMemberId memberId = GroupMemberId.builder()
                .personId(requestDto.getPersonId())
                .pgroupId(groupId)
                .build();

        GroupMember newGroupMember = GroupMember.builder()
                .groupMemberId(memberId)
                .role(newRole)
                .build();

        return saveGroupRole(newRole).flatMap(role -> {
            newGroupMember.setRole(role);
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

    private Mono<GroupRole> saveGroupRole(GroupRole groupRole) {
        return Mono.fromCallable(() -> groupRoleRepository.save(groupRole))
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
