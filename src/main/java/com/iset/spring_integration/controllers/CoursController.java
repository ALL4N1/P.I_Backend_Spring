package com.iset.spring_integration.controllers;

import com.iset.spring_integration.dto.CahpitreDTO;
import com.iset.spring_integration.dto.CoursDTO;
import com.iset.spring_integration.entities.Chapitre;
import com.iset.spring_integration.entities.Cours;
import com.iset.spring_integration.entities.Developpeur;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.repositories.DeveloppeurRepository;
import com.iset.spring_integration.repositories.EnseignantRepository;
import com.iset.spring_integration.services.CoursService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cours")
@CrossOrigin(origins = "*")
public class CoursController {
    @Autowired
    private CoursService coursService;

    @Autowired
    private EnseignantRepository enseignantRepository;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCours(@PathVariable Long id) {
        coursService.deleteCours(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> findAllCours() {
        List<Map<String, Object>> simplifiedCourses = coursService.findAllCours().stream()
                .map(cours -> {
                    Map<String, Object> coursMap = new HashMap<>();
                    coursMap.put("id", cours.getId());
                    coursMap.put("titre", cours.getTitre());
                    coursMap.put("contenu", cours.getContenu());
                    coursMap.put("image_url", cours.getImage_url());
                    coursMap.put("subject", cours.getSubject());

                    // Add simplified teacher info
                    if (cours.getEnseignant() != null) {
                        Map<String, Object> teacherMap = new HashMap<>();
                        teacherMap.put("id", cours.getEnseignant().getId());
                        teacherMap.put("nom", cours.getEnseignant().getNom());
                        teacherMap.put("email", cours.getEnseignant().getEmail());
                        coursMap.put("enseignant", teacherMap);
                    }

                    return coursMap;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(simplifiedCourses);
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addCours(@RequestBody CoursDTO request) {
        Cours savedCours = coursService.addCours(request);

        Map<String, Object> response = new HashMap<>();
        response.put("id", savedCours.getId());
        response.put("titre", savedCours.getTitre());
        response.put("contenu", savedCours.getContenu());
        response.put("subject", savedCours.getSubject());

        if (savedCours.getEnseignant() != null) {
            Map<String, Object> teacherMap = new HashMap<>();
            teacherMap.put("id", savedCours.getEnseignant().getId());
            teacherMap.put("nom", savedCours.getEnseignant().getNom());
            response.put("enseignant", teacherMap);
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/add/chapitre")
    public ResponseEntity<Map<String, Object>> addChapitre(@RequestBody CahpitreDTO request) {
        Chapitre savedChapitre = coursService.addChapitre(request);

        Map<String, Object> response = new HashMap<>();
        response.put("id", savedChapitre.getId());
        response.put("titre", savedChapitre.getTitre());
        response.put("urlChapitre", savedChapitre.getUrlChapitre());
        response.put("typeChapitre", savedChapitre.getTypeChapitre());
        response.put("placement", savedChapitre.getPlacement());

        if (savedChapitre.getCours() != null) {
            Map<String, Object> coursMap = new HashMap<>();
            coursMap.put("id", savedChapitre.getCours().getId());
            coursMap.put("titre", savedChapitre.getCours().getTitre());
            response.put("cours", coursMap);
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCoursById(@PathVariable Long id) {
        try {
            Cours cours = coursService.findAllCours().stream()
                    .filter(c -> c.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));

            List<Chapitre> chapitres = coursService.findChapitreByCours(id);

            Map<String, Object> response = new HashMap<>();
            response.put("course", cours);
            response.put("chapitres", chapitres);

            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving course: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/chapitres")
    public ResponseEntity<List<Map<String, Object>>> findChapitreByCours(@PathVariable Long id) {
        List<Chapitre> chapitres = coursService.findChapitreByCours(id);

        List<Map<String, Object>> simplifiedChapitres = chapitres.stream()
                .map(chapitre -> {
                    Map<String, Object> chapitreMap = new HashMap<>();
                    chapitreMap.put("id", chapitre.getId());
                    chapitreMap.put("titre", chapitre.getTitre());
                    chapitreMap.put("urlChapitre", chapitre.getUrlChapitre());
                    chapitreMap.put("typeChapitre", chapitre.getTypeChapitre());
                    chapitreMap.put("placement", chapitre.getPlacement());
                    return chapitreMap;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(simplifiedChapitres);
    }

    @GetMapping("/subjects")
    public ResponseEntity<List<String>> findAllSubjects() {
        return ResponseEntity.ok(coursService.getAllSubjects());
    }

    @GetMapping("/enseignant/{id}")
    public ResponseEntity<?> findCoursByEnseignant(@PathVariable Long id) {
        try {
            Enseignant enseignant = enseignantRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Enseignant not found with id: " + id));

            List<Cours> courses = coursService.findAllCoursByEnseignant(enseignant);

            // Convert to simplified objects
            List<Map<String, Object>> simplifiedCourses = courses.stream()
                    .map(cours -> {
                        Map<String, Object> coursMap = new HashMap<>();
                        coursMap.put("id", cours.getId());
                        coursMap.put("titre", cours.getTitre());
                        coursMap.put("contenu", cours.getContenu());
                        coursMap.put("image_url", cours.getImage_url());
                        coursMap.put("subject", cours.getSubject());
                        return coursMap;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(simplifiedCourses);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving courses: " + e.getMessage());
        }
    }
}