package com.iset.spring_integration.services;

import com.iset.spring_integration.dto.CoursDTO;
import com.iset.spring_integration.entities.Cours;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.repositories.CoursRepository;
import com.iset.spring_integration.repositories.EnseignantRepository;
import com.iset.spring_integration.util.FileStore;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoursService {
    @Autowired
    private CoursRepository coursRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;

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
        FileStore fs = new FileStore("uploads/cours_bg");
        cours.setImage_url(fs.store(request.getImage()));
        return coursRepository.save(cours);
    }

    public List<String> getAllSubjects() {
        List<String> subjects = new ArrayList<>(coursRepository.getSubjects());
        return subjects;
    }

    public List<Cours> findAllCoursByEnseignant(Enseignant enseignant) {
        return coursRepository.findCoursByEnseignant(enseignant);
    }
}
