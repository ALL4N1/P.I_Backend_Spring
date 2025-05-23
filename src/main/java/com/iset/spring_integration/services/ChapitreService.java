package com.iset.spring_integration.services;

import com.iset.spring_integration.dto.ChapitreDTO;
import com.iset.spring_integration.entities.Chapitre;
import com.iset.spring_integration.entities.Cours;
import com.iset.spring_integration.repositories.ChapitreRepository;
import com.iset.spring_integration.repositories.CoursRepository;
import com.iset.spring_integration.repositories.EnseignantRepository;
import com.iset.spring_integration.util.FileStore;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapitreService {
    @Autowired
    private CoursRepository coursRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private ChapitreRepository chapitreRepository;

    public Chapitre addChapitre(ChapitreDTO request) {
        Cours cours = coursRepository.findById(request.getId_cours()).orElseThrow(
                () -> new EntityNotFoundException("Cours not found")
        );
        Chapitre chapitre = new Chapitre();
        chapitre.setCours(cours);
        chapitre.setTitre(request.getTitre());

        chapitre.setTypeChapitre(request.getType_chapitre());
        chapitre.setPlacement(request.getPlacement());

        FileStore fs = new FileStore("uploads/chapitres/"+request.getType_chapitre());
        chapitre.setUrlChapitre(fs.store(request.getChapitre_file()));
        return chapitreRepository.save(chapitre);
    }

    public List<Chapitre> findChapitreByCours(Long id) {
        Cours cours = coursRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cours not found"));
        return chapitreRepository.findChapitreByCours(cours);
    }
}
