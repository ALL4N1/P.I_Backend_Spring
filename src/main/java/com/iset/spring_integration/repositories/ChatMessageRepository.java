package com.iset.spring_integration.repositories;

import com.iset.spring_integration.entities.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findBySenderIdAndRecipientIdOrSenderIdAndRecipientId(
            Long senderId1, Long recipientId1, Long senderId2, Long recipientId2
    );

    // Dans le repository
    @Query("SELECT m FROM ChatMessage m WHERE " +
            "(m.senderId = :user1Id AND m.recipientId = :user2Id) OR " +
            "(m.senderId = :user2Id AND m.recipientId = :user1Id) " +
            "ORDER BY m.timestamp DESC LIMIT 50")
    List<ChatMessage> findRecentMessages(@Param("user1Id") Long user1Id,
                                         @Param("user2Id") Long user2Id);
}
