package com.highload.chatic.service.impls;

import com.highload.chatic.dto.GroupRoleDto;
import com.highload.chatic.dto.PageResponseDto;
import com.highload.chatic.dto.group.GroupMemberDto;
import com.highload.chatic.dto.group.GroupMemberResponseDto;
import com.highload.chatic.dto.personalchat.PersonalChatResponseDto;
import com.highload.chatic.exception.IllegalAccessException;
import com.highload.chatic.exception.ResourceNotFoundException;
import com.highload.chatic.models.GroupMember;
import com.highload.chatic.models.GroupMemberId;
import com.highload.chatic.models.GroupRole;
import com.highload.chatic.models.PGroup;
import com.highload.chatic.repository.GroupMemberRepository;
import com.highload.chatic.repository.GroupRoleRepository;
import com.highload.chatic.repository.PGroupRepository;
import com.highload.chatic.service.GroupService;
import com.highload.chatic.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupMemberRepository groupMemberRepository;
    private final ModelMapper modelMapper;
    private final PersonService personService;
    private final PGroupRepository groupRepository;
    private final GroupRoleRepository groupRoleRepository;

    @Override
    public GroupMemberDto getGroupMemberInfo(UUID group, UUID personId) {
        var groupMemberInfo = groupMemberRepository
                .findByGroupMemberId(new GroupMemberId(group, personId))
                .orElseThrow(ResourceNotFoundException::new);
        return modelMapper.map(groupMemberInfo, GroupMemberDto.class);
    }

    @Override
    public void addGroup(String username) {
        var person = personService.getPerson(username);
        PGroup group = new PGroup();
        groupRepository.save(group);
        GroupRole groupRole = new GroupRole(true, true, true);
        groupRoleRepository.save(groupRole);
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupMemberId(new GroupMemberId(group.getId(), person.getId()));
        groupMember.setRole(groupRole);
        groupMemberRepository.save(groupMember);
    }

    @Override
    public PageResponseDto<GroupMemberResponseDto> getMembers(String username, UUID groupId, Pageable pageable) {
        if (isGroupMember(username, groupId).isEmpty()) throw new IllegalAccessException();
        var page = groupMemberRepository
                .findGroupMembers(groupId, pageable)
                .map(it -> modelMapper.map(it, GroupMemberResponseDto.class));
        return new PageResponseDto<>(page);
    }

    @Override
    public GroupMemberResponseDto getMember(String username, UUID groupId, UUID personId) {
        if (isGroupMember(username, groupId).isEmpty()) throw new IllegalAccessException();
        var member = groupMemberRepository.findByGroupMemberId(new GroupMemberId(groupId, personId));
        if (member.isEmpty()) throw new ResourceNotFoundException();
        return modelMapper.map(member.get(), GroupMemberResponseDto.class);
    }

    @Override
    public void addMember(String username, UUID groupId, UUID personId, GroupRoleDto groupRoleDto) {
        var person = isGroupMember(username, groupId);
        if (person.isEmpty()) throw new IllegalAccessException();
        if (!person.get().getRole().isManageMembers()) throw new IllegalAccessException();
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupMemberId(new GroupMemberId(groupId, personId));
        GroupRole groupRole = modelMapper.map(groupRoleDto, GroupRole.class);
        groupRoleRepository.save(groupRole);
        groupMember.setRole(groupRole);
        groupMemberRepository.save(groupMember);
    }

    @Override
    public void deleteMember(String username, UUID groupId, UUID personId) {
        var person = isGroupMember(username, groupId);
        if (person.isEmpty()) throw new IllegalAccessException();
        if (!person.get().getRole().isManageMembers()) throw new IllegalAccessException();
        var member = groupMemberRepository.findByGroupMemberId(new GroupMemberId(groupId, personId));
        if (member.isEmpty()) throw new ResourceNotFoundException();
        groupMemberRepository.delete(member.get());
    }


    private Optional<GroupMember> isGroupMember (String username, UUID groupId) {
        var person = personService.getPerson(username);
        return groupMemberRepository.findByGroupMemberId(new GroupMemberId(groupId, person.getId()));
    }
}
