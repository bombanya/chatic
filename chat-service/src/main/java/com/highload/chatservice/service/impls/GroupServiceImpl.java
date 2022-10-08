package com.highload.chatservice.service.impls;

import com.highload.chatservice.dto.GroupMemberDto;
import com.highload.chatservice.exception.ResourceNotFoundException;
import com.highload.chatservice.models.GroupMemberId;
import com.highload.chatservice.repository.GroupMemberRepository;
import com.highload.chatservice.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupMemberRepository groupMemberRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<GroupMemberDto> getGroupMemberInfo(UUID group, UUID personId) {
                //.orElseThrow(ResourceNotFoundException::new);
        return groupMemberRepository.findByGroupMemberId(new GroupMemberId(group, personId))
                .map(
                        gm -> modelMapper.map(gm, GroupMemberDto.class)
                );
                //.onErrorResume(ResourceNotFoundException.class, e -> n);
    }
}
