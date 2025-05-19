package com.iset.spring_integration.controllers;

import com.iset.spring_integration.component.PresenceEventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class PresenceController {

    private final PresenceEventListener presenceEventListener;

    public PresenceController(PresenceEventListener presenceEventListener) {
        this.presenceEventListener = presenceEventListener;
    }

    @GetMapping("/{userId}/status")
    public ResponseEntity<Map<String, Object>> getUserStatus(@PathVariable Long userId) {
        return ResponseEntity.ok(
                Map.of(
                        "userId", userId,
                        "online", presenceEventListener.isUserOnline(userId),
                        "lastSeen", LocalDateTime.now().toString() // À adapter avec un vrai suivi
                )
        );
    }
}