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
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupMemberRepository groupMemberRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<GroupMemberDto> getGroupMemberInfo(UUID group, UUID personId) {
        GroupMemberId groupMemberId = GroupMemberId.builder()
                .personId(personId)
                .pgroupId(group)
                .build();
        return Mono.fromCallable(() -> groupMemberRepository.findByGroupMemberId(groupMemberId)
                        .orElseThrow(ResourceNotFoundException::new))
                .subscribeOn(Schedulers.boundedElastic())
                .map(groupMember -> modelMapper.map(groupMember, GroupMemberDto.class));
    }
}
