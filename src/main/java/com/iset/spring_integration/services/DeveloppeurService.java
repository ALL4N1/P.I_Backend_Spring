package com.iset.spring_integration.services;

import com.iset.spring_integration.entities.Developpeur;
import com.iset.spring_integration.repositories.DeveloppeurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloppeurService {

    @Autowired
    private DeveloppeurRepository developpeurRepository;

    // Approuver la candidature d’un développeur (changer le rôle en "PROFESSIONNEL")
    public void approveDeveloper(Long id) {
        Developpeur dev = developpeurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Développeur non trouvé"));
        dev.setRole("PROFESSIONNEL");
        developpeurRepository.save(dev);
    }

    // Rétrograder un développeur (changer le rôle en "DEVELOPPEUR")
    public void demoteDeveloper(Long id) {
        Developpeur dev = developpeurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Développeur non trouvé"));
        dev.setRole("DEVELOPPEUR");
        developpeurRepository.save(dev);
    }

    // Supprimer un développeur
    public void deleteDeveloper(Long id) {
        if (!developpeurRepository.existsById(id)) {
            throw new EntityNotFoundException("Développeur non trouvé");
        }
        developpeurRepository.deleteById(id);
    }
}

