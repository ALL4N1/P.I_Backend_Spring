package com.iset.spring_integration.controllers;

import com.iset.spring_integration.entities.ChatMessage;
import com.iset.spring_integration.repositories.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class ChatMessageRestController {

    private final ChatMessageRepository messageRepository;

    public ChatMessageRestController(ChatMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    @GetMapping("/{user1Id}/{user2Id}")
    public List<ChatMessage> getMessages(
            @PathVariable Long user1Id,
            @PathVariable Long user2Id
    ) {
        return messageRepository.findBySenderIdAndRecipientIdOrSenderIdAndRecipientId(
                user1Id, user2Id, user2Id, user1Id
        );
    }
}

