package com.iset.spring_integration.services;

import com.iset.spring_integration.entities.Warning;
import com.iset.spring_integration.entities.Utilisateur;
import com.iset.spring_integration.repositories.WarningRepository;
import com.iset.spring_integration.repositories.UtilisateurRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WarningService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public void sendWarning(Long userId, String message) {
        Utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
        // Implémenter la logique d'envoi d'avertissement (e.g., email, notification)
    }
}
