package com.iset.spring_integration.services;

import com.iset.spring_integration.dto.ProjetDTO;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.entities.Projet;
import com.iset.spring_integration.repositories.EnseignantRepository;
import com.iset.spring_integration.repositories.ProjetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetService {
    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private EnseignantRepository enseignantRepository;

    public List<Projet> findAllProjets() {
        return projetRepository.findAll();
    }

    public void deleteProjet(Long id) {
        projetRepository.deleteById(id);
    }

    public Projet addProjet(ProjetDTO request) {
        Enseignant enseignant = enseignantRepository.findById(request.getEnseignant_id())
                .orElseThrow(() -> new EntityNotFoundException("Enseignant not found"));

        Projet projet = new Projet();
        projet.setEnseignant(enseignant);
        projet.setTitre(request.getTitre());
        projet.setDescription(request.getDescription());
        projet.setGithubLink(request.getGithubLink());

        return projetRepository.save(projet);
    }

    public List<Projet> findProjetsByEnseignant(Enseignant enseignant) {
        return projetRepository.findByEnseignant(enseignant);
    }
}