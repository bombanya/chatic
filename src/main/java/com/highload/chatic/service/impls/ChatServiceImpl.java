package com.highload.chatic.service.impls;

import com.highload.chatic.dto.GroupMemberDto;
import com.highload.chatic.exception.IllegalAccessException;
import com.highload.chatic.exception.ResourceNotFoundException;
import com.highload.chatic.models.MessageOperation;
import com.highload.chatic.models.PGroup;
import com.highload.chatic.models.PersonalChat;
import com.highload.chatic.repository.ChatRepository;
import com.highload.chatic.service.ChatService;
import com.highload.chatic.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final GroupService groupService;

    @Override
    public void authorizeOperation(UUID chatId, UUID personId, MessageOperation operation) {
        var chat = chatRepository.findById(chatId).orElseThrow(ResourceNotFoundException::new);
        if (chat instanceof PersonalChat) authorizeInPersonalChat(personId, (PersonalChat) chat);
        else if (chat instanceof PGroup)
            authorizeInGroup(groupService.getGroupMemberInfo(chatId, personId), operation);
        else throw new IllegalAccessException();
    }

    private void authorizeInPersonalChat(UUID personId, PersonalChat personalChat) {
        if (personalChat.getPerson1Id().equals(personId) || personalChat.getPerson2Id().equals(personId)) return;
        throw new IllegalAccessException();
    }

    private void authorizeInGroup(GroupMemberDto groupMemberDto, MessageOperation operation) {
        if (operation == MessageOperation.READ) return;
        else if (operation == MessageOperation.WRITE && groupMemberDto.getRole().isWritePosts()) return;
        else if (operation == MessageOperation.REPLY && groupMemberDto.getRole().isWriteComments()) return;
        throw new IllegalAccessException();
    }
}
