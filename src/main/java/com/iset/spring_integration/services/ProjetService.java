package com.iset.spring_integration.services;

import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.entities.Projet;
import com.iset.spring_integration.repositories.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetService {

    @Autowired
    private ProjetRepository projetRepository;

    public List<Projet> findByEnseignantId(Long enseignantId) {
        return projetRepository.findByEnseignantId(enseignantId);
    }

    public Projet save(Projet projet) {
        return projetRepository.save(projet);
    }

    public void deleteById(Long id) {
        projetRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return projetRepository.existsById(id);
    }
}