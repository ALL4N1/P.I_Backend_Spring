package com.iset.spring_integration.controllers;

import com.iset.spring_integration.dto.ProjetDTO;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.entities.Projet;
import com.iset.spring_integration.repositories.EnseignantRepository;
import com.iset.spring_integration.services.ProjetService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projets")
@CrossOrigin(origins = "*")
public class ProjetController {
    @Autowired
    private ProjetService projetService;

    @Autowired
    private EnseignantRepository enseignantRepository;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjet(@PathVariable Long id) {
        projetService.deleteProjet(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> findAllProjets() {
        List<Map<String, Object>> simplifiedProjets = projetService.findAllProjets().stream()
                .map(projet -> {
                    Map<String, Object> projetMap = new HashMap<>();
                    projetMap.put("id", projet.getId());
                    projetMap.put("titre", projet.getTitre());
                    projetMap.put("description", projet.getDescription());
                    projetMap.put("githubLink", projet.getGithubLink());

                    if (projet.getEnseignant() != null) {
                        Map<String, Object> teacherMap = new HashMap<>();
                        teacherMap.put("id", projet.getEnseignant().getId());
                        teacherMap.put("nom", projet.getEnseignant().getNom());
                        projetMap.put("enseignant", teacherMap);
                    }

                    return projetMap;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(simplifiedProjets);
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addProjet(@RequestBody ProjetDTO request) {
        Projet savedProjet = projetService.addProjet(request);

        Map<String, Object> response = new HashMap<>();
        response.put("id", savedProjet.getId());
        response.put("titre", savedProjet.getTitre());
        response.put("description", savedProjet.getDescription());
        response.put("githubLink", savedProjet.getGithubLink());

        if (savedProjet.getEnseignant() != null) {
            Map<String, Object> teacherMap = new HashMap<>();
            teacherMap.put("id", savedProjet.getEnseignant().getId());
            teacherMap.put("nom", savedProjet.getEnseignant().getNom());
            response.put("enseignant", teacherMap);
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/enseignant/{id}")
    public ResponseEntity<?> getProjetsByEnseignant(@PathVariable Long id) {
        try {
            Enseignant enseignant = enseignantRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Enseignant not found"));

            List<Map<String, Object>> simplifiedProjets = projetService.findProjetsByEnseignant(enseignant).stream()
                    .map(projet -> {
                        Map<String, Object> projetMap = new HashMap<>();
                        projetMap.put("id", projet.getId());
                        projetMap.put("titre", projet.getTitre());
                        projetMap.put("description", projet.getDescription());
                        projetMap.put("githubLink", projet.getGithubLink());
                        return projetMap;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(simplifiedProjets);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}