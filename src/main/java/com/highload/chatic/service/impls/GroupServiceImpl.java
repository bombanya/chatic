package com.highload.chatic.service.impls;

import com.highload.chatic.dto.GroupMemberDto;
import com.highload.chatic.exception.ResourceNotFoundException;
import com.highload.chatic.models.GroupMemberId;
import com.highload.chatic.repository.GroupMemberRepository;
import com.highload.chatic.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupMemberRepository groupMemberRepository;
    private final ModelMapper modelMapper;

    @Override
    public GroupMemberDto getGroupMemberInfo(UUID group, UUID personId) {
        var groupMemberInfo = groupMemberRepository
                .findByGroupMemberId(new GroupMemberId(group, personId))
                .orElseThrow(ResourceNotFoundException::new);
        return modelMapper.map(groupMemberInfo, GroupMemberDto.class);
    }
}
