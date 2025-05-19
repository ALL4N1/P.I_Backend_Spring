package com.iset.spring_integration.controllers;

import com.iset.spring_integration.dto.UtilisateurChatDTO;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.entities.Utilisateur;
import com.iset.spring_integration.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurChatController {
    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurChatController(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }
    @GetMapping("/all")
    public List<UtilisateurChatDTO> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        return utilisateurs.stream().map(u -> {
            if (u instanceof Enseignant enseignant) {
                return new UtilisateurChatDTO(
                        enseignant.getId(),
                        enseignant.getNom(),
                        enseignant.getEmail(),
                        enseignant.getPfp_url(),
                        new HashSet<>(enseignant.getBadges())
                );
            } else {
                return new UtilisateurChatDTO(
                        u.getId(),
                        u.getNom(),
                        u.getEmail(),
                        u.getPfp_url(),
                        null
                );
            }
        }).toList();
    }
}
