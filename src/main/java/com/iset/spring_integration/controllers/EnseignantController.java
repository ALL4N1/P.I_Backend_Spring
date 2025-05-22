package com.iset.spring_integration.controllers;

import com.iset.spring_integration.dto.UtilisateurChatDTO;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.entities.Cours;
import com.iset.spring_integration.repositories.EnseignantRepository;
import com.iset.spring_integration.services.CoursService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enseignants")
@CrossOrigin(origins = "*")
public class EnseignantController {

    private final CoursService coursService;
    private final EnseignantRepository enseignantRepository;

    public EnseignantController(
            EnseignantRepository enseignantRepository,
            CoursService coursService) {
        this.enseignantRepository = enseignantRepository;
        this.coursService = coursService;
    }

    @GetMapping("/list")
    public List<UtilisateurChatDTO> getAllEnseignants() {
        return enseignantRepository.findAll()
                .stream()
                .map(e -> {
                    UtilisateurChatDTO dto = new UtilisateurChatDTO();
                    dto.setId(e.getId());
                    dto.setNom(e.getNom());
                    dto.setEmail(e.getEmail());
                    dto.setPfp_url(e.getPfp_url());
                    dto.setBadges(e.getBadges());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<Map<String, Object>> getAllEnseignantsWithCourses() {
        return enseignantRepository.findAll()
                .stream()
                .map(e -> {
                    Map<String, Object> teacher = new HashMap<>();
                    teacher.put("id", e.getId());
                    teacher.put("nom", e.getNom());
                    teacher.put("email", e.getEmail());
                    teacher.put("bio", e.getBio());
                    teacher.put("pfp_url", e.getPfp_url());
                    teacher.put("tel", e.getTel());
                    teacher.put("badges", e.getBadges());

                    // Fetch courses for this teacher and create simplified objects
                    List<Map<String, Object>> simplifiedCourses = new ArrayList<>();
                    for (Cours cours : coursService.findAllCoursByEnseignant(e)) {
                        Map<String, Object> coursMap = new HashMap<>();
                        coursMap.put("id", cours.getId());
                        coursMap.put("titre", cours.getTitre());
                        coursMap.put("contenu", cours.getContenu());
                        coursMap.put("image_url", cours.getImage_url());
                        coursMap.put("subject", cours.getSubject());
                        simplifiedCourses.add(coursMap);
                    }
                    teacher.put("courses", simplifiedCourses);

                    return teacher;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getEnseignantDetails(@PathVariable Long id) {
        try {
            Enseignant enseignant = enseignantRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

            Map<String, Object> response = new HashMap<>();
            response.put("id", enseignant.getId());
            response.put("nom", enseignant.getNom());
            response.put("email", enseignant.getEmail());
            response.put("bio", enseignant.getBio());
            response.put("pfp_url", enseignant.getPfp_url());
            response.put("tel", enseignant.getTel());
            response.put("badges", enseignant.getBadges());

            // Create simple map objects for courses to avoid circular references
            List<Map<String, Object>> simplifiedCourses = new ArrayList<>();
            for (Cours cours : coursService.findAllCoursByEnseignant(enseignant)) {
                Map<String, Object> coursMap = new HashMap<>();
                coursMap.put("id", cours.getId());
                coursMap.put("titre", cours.getTitre());
                coursMap.put("contenu", cours.getContenu());
                coursMap.put("image_url", cours.getImage_url());
                coursMap.put("subject", cours.getSubject());
                simplifiedCourses.add(coursMap);
            }
            response.put("courses", simplifiedCourses);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEnseignant(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        Enseignant enseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        if (updates.containsKey("bio")) {
            enseignant.setBio((String) updates.get("bio"));
        }

        enseignantRepository.save(enseignant);
        return ResponseEntity.ok().build();
    }
}