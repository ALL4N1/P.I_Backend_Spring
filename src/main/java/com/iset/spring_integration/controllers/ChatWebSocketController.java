package com.iset.spring_integration.controllers;

import com.iset.spring_integration.dto.ChatMessageDTO;
import com.iset.spring_integration.dto.TypingNotification;
import com.iset.spring_integration.entities.ChatMessage;
import com.iset.spring_integration.entities.Utilisateur;
import com.iset.spring_integration.repositories.ChatMessageRepository;
import com.iset.spring_integration.repositories.UtilisateurRepository;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class ChatWebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;
    private final UtilisateurRepository utilisateurRepository;



    public ChatWebSocketController(SimpMessagingTemplate messagingTemplate,
                                   ChatMessageRepository chatMessageRepository,
                                    UtilisateurRepository utilisateurRepository

                                  ) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageRepository = chatMessageRepository;
        this.utilisateurRepository = utilisateurRepository;
    }



    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessageDTO message, Principal principal) {
        // Récupérer l'utilisateur connecté via son email (username)
        String username = ((UserDetails) ((Authentication) principal).getPrincipal()).getUsername();

        // Chargez votre entité Utilisateur depuis la base (exemple via repository)
        Utilisateur utilisateur = utilisateurRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        // Vérifiez que l'expéditeur est bien celui qui est authentifié
        if (!utilisateur.getId().equals(message.getSenderId())) {
            throw new SecurityException("Tentative d'usurpation d'identité");
        }
        System.out.println("Message reçu du frontend : " + message.getContent() +
                " de " + message.getSenderId() + " à " + message.getRecipientId());

        ChatMessage saved = chatMessageRepository.save(new ChatMessage(
                null,
                message.getSenderId(),
                message.getRecipientId(),
                message.getContent(),
                LocalDateTime.now(),
                false
        ));

        // Envoyer au destinataire
        messagingTemplate.convertAndSendToUser(
                message.getRecipientId().toString(),
                "/queue/messages",
                saved
        );

        // Envoyer une copie à l'expéditeur (optionnel)
        messagingTemplate.convertAndSendToUser(
                message.getSenderId().toString(),
                "/queue/messages",
                saved
        );
    }

    @MessageMapping("/chat.typing")
    public void handleTyping(TypingNotification notification) {
        messagingTemplate.convertAndSendToUser(
                notification.getRecipientId().toString(),
                "/queue/typing",
                Map.of(
                        "senderId", notification.getSenderId(),
                        "isTyping", notification.isTyping()
                )
        );
    }

    @MessageMapping("/chat.read")
    public void markAsRead(@Payload Long messageId) {
        ChatMessage message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message non trouvé"));
        message.setRead(true);
        chatMessageRepository.save(message);
    }


}
