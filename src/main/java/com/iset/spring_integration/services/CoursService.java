package com.iset.spring_integration.services;

import com.iset.spring_integration.dto.CahpitreDTO;
import com.iset.spring_integration.dto.CoursDTO;
import com.iset.spring_integration.entities.Chapitre;
import com.iset.spring_integration.entities.Cours;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.repositories.ChapitreRepository;
import com.iset.spring_integration.repositories.CoursRepository;
import com.iset.spring_integration.repositories.EnseignantRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoursService {
    @Autowired
    private CoursRepository coursRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private ChapitreRepository chapitreRepository;

    public void deleteCours(Long id) {
        coursRepository.deleteById(id);
    }

    public List<Cours> findAllCours() {
        return coursRepository.findAll();
    }

    public Cours addCours(CoursDTO request) {
        Enseignant enseignant = enseignantRepository.findById(request.getEnseignant_id()).orElseThrow(
                () -> new EntityNotFoundException("Enseignant not found"));
        Cours cours = new Cours();
        cours.setEnseignant(enseignant);
        cours.setContenu(request.getContenu());
        cours.setSubject(request.getSubject());
        cours.setTitre(request.getTitre());
        return coursRepository.save(cours);
    }

    public Chapitre addChapitre(CahpitreDTO request) {
        Cours cours = coursRepository.findById(request.getId_cours()).orElseThrow(
                () -> new EntityNotFoundException("Cours not found")
        );
        Chapitre chapitre = new Chapitre();
        chapitre.setCours(cours);
        chapitre.setTitre(request.getTitre());
        chapitre.setUrlChapitre(request.getUrl_chapitre());
        chapitre.setTypeChapitre(request.getType_chapitre());
        chapitre.setPlacement(request.getPlacement());
        return chapitreRepository.save(chapitre);
    }

    public List<Chapitre> findChapitreByCours(Long id) {
        Cours cours = coursRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cours not found"));
        return chapitreRepository.findChapitreByCours(cours);
    }

    public List<String> getAllSubjects() {
        List<String> subjects = new ArrayList<>(coursRepository.getSubjects());
        return subjects;
    }

    public List<Cours> findAllCoursByEnseignant(Enseignant enseignant) {
        return coursRepository.findCoursByEnseignant(enseignant);
    }
}
