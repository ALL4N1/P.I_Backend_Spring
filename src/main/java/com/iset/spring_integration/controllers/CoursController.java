package com.iset.spring_integration.controllers;

import com.iset.spring_integration.dto.ChapitreDTO;
import com.iset.spring_integration.dto.CoursDTO;
import com.iset.spring_integration.entities.Chapitre;
import com.iset.spring_integration.entities.Cours;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.entities.TypeChapitre;
import com.iset.spring_integration.repositories.ChapitreRepository;
import com.iset.spring_integration.repositories.CoursRepository;
import com.iset.spring_integration.repositories.EnseignantRepository;
import com.iset.spring_integration.services.ChapitreService;
import com.iset.spring_integration.services.CoursService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cours")
@CrossOrigin(origins = "*")
public class CoursController {
    @Autowired
    private CoursService coursService;

    @Autowired
    private ChapitreRepository chapitreRepository;

    @Autowired
    private ChapitreService chapitreService;

    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private CoursRepository coursRepository;

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


    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Cours> addCours(
            @RequestParam("titre") String titre,
            @RequestParam("contenu") String contenu,
            @RequestParam("subject") String subject,
            @RequestParam("enseignant_id") String enseignantId,
            @RequestParam(value = "image", required = false) MultipartFile image
    )  {
        CoursDTO coursDTO = new CoursDTO();
        coursDTO.setTitre(titre);
        coursDTO.setContenu(contenu);
        coursDTO.setSubject(subject);
        coursDTO.setEnseignant_id(Long.parseLong(enseignantId));
        coursDTO.setImage(image);

        Cours savedCours = coursService.addCours(coursDTO);

        return new ResponseEntity<>(savedCours, HttpStatus.CREATED);
    }

    @PostMapping("/add/chapitre")
    public ResponseEntity<Map<String, Object>> addChapitre(@RequestBody ChapitreDTO request) {
        Chapitre savedChapitre = chapitreService.addChapitre(request);

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

            List<Chapitre> chapitres = chapitreService.findChapitreByCours(id);

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
        List<Chapitre> chapitres = chapitreService.findChapitreByCours(id);

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

    @GetMapping("/{id}/details")
    public ResponseEntity<?> findCoursByDetails(@PathVariable Long id) {
        return ResponseEntity.ok(coursRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Course not found with id: " + id)));
    }

    @PostMapping(value = "/chapitre/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Chapitre> addChapitre(
            @RequestParam("titre") String titre,
            @RequestParam("placement") String placement,
            @RequestParam("type_chapter") String type_chapter,
            @RequestParam("cours_id") String cours_id,
            @RequestParam(value = "chapter_file", required = false) MultipartFile file
    )  {
        ChapitreDTO chapitreDTO = new ChapitreDTO();
        chapitreDTO.setTitre(titre);
        chapitreDTO.setPlacement(Integer.parseInt(placement));
        chapitreDTO.setId_cours(Long.parseLong(cours_id));
        chapitreDTO.setChapitre_file(file);
        chapitreDTO.setType_chapitre(TypeChapitre.valueOf(type_chapter));

        Chapitre savedChapitre = chapitreService.addChapitre(chapitreDTO);

        return new ResponseEntity<>(savedChapitre, HttpStatus.CREATED);
    }

}