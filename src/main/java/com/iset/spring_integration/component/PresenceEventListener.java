package com.iset.spring_integration.component;

import com.iset.spring_integration.dto.CustomUserDetails;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PresenceEventListener {
    private final SimpMessagingTemplate messagingTemplate;
    private final ConcurrentHashMap<Long, Boolean> onlineUsers = new ConcurrentHashMap<>();

    public PresenceEventListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Authentication auth = (Authentication) accessor.getUser();

        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            Long userId = userDetails.getId();
            onlineUsers.put(userId, true);

            messagingTemplate.convertAndSend("/topic/presence",
                    Map.of(
                            "userId", userId,
                            "status", "online",
                            "timestamp", LocalDateTime.now().toString()
                    )
            );
        }
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Authentication auth = (Authentication) accessor.getUser();

        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            Long userId = userDetails.getId();
            onlineUsers.remove(userId);

            messagingTemplate.convertAndSend("/topic/presence",
                    Map.of(
                            "userId", userId,
                            "status", "offline",
                            "timestamp", LocalDateTime.now().toString()
                    )
            );
        }
    }

    public boolean isUserOnline(Long userId) {
        return onlineUsers.containsKey(userId);
    }
}
