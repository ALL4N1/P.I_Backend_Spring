package com.iset.spring_integration.services;

import com.iset.spring_integration.entities.Utilisateur;
import com.iset.spring_integration.repositories.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public void changeStatus(Long userId, String status) {
        Utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
        user.setStatut(status); // Assurez-vous que le champ 'statut' existe dans l'entité Utilisateur
        utilisateurRepository.save(user);
    }
}
