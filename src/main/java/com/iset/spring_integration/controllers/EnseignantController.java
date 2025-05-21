package com.iset.spring_integration.controllers;

import com.iset.spring_integration.dto.UtilisateurChatDTO;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.entities.Projet;
import com.iset.spring_integration.entities.Certification;
import com.iset.spring_integration.repositories.EnseignantRepository;
import com.iset.spring_integration.repositories.ProjetRepository;
import com.iset.spring_integration.repositories.CertificationRepository;
import com.iset.spring_integration.services.CoursService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enseignants")
@CrossOrigin(origins = "*")
public class EnseignantController {

    private final CoursService coursService;
    private final EnseignantRepository enseignantRepository;
    private final ProjetRepository projetRepository;
    private final CertificationRepository certificationRepository;
    private static final String CERTIFICATIONS_UPLOAD_DIR = "uploads/certifications/";

    public EnseignantController(
            EnseignantRepository enseignantRepository,
            ProjetRepository projetRepository,
            CertificationRepository certificationRepository,
            CoursService coursService) {
        this.enseignantRepository = enseignantRepository;
        this.projetRepository = projetRepository;
        this.certificationRepository = certificationRepository;
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

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getEnseignantDetails(@PathVariable Long id) {
        Enseignant enseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        Map<String, Object> response = new HashMap<>();
        response.put("id", enseignant.getId());
        response.put("nom", enseignant.getNom());
        response.put("email", enseignant.getEmail());
        response.put("pfp_url", enseignant.getPfp_url());
        response.put("tel", enseignant.getTel());
        response.put("bio", enseignant.getBio());
        response.put("courses", coursService.findAllCoursByEnseignant(enseignant));
        response.put("position", enseignant.getPosition());
        response.put("university", enseignant.getUniversity());
        response.put("badges", enseignant.getBadges());
        response.put("projets", enseignant.getProjets());
        response.put("certifications", enseignant.getCertifications());

        return ResponseEntity.ok(response);
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
        if (updates.containsKey("position")) {
            enseignant.setPosition((String) updates.get("position"));
        }
        if (updates.containsKey("university")) {
            enseignant.setUniversity((String) updates.get("university"));
        }

        enseignantRepository.save(enseignant);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/projets")
    public ResponseEntity<Projet> addProjet(
            @PathVariable Long id,
            @RequestBody Projet projet) {

        Enseignant enseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        projet.setEnseignant(enseignant);
        Projet savedProjet = projetRepository.save(projet);
        return ResponseEntity.ok(savedProjet);
    }

    @DeleteMapping("/projets/{projetId}")
    public ResponseEntity<?> deleteProjet(@PathVariable Long projetId) {
        if (!projetRepository.existsById(projetId)) {
            throw new EntityNotFoundException("Project not found");
        }
        projetRepository.deleteById(projetId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/certifications")
    public ResponseEntity<Certification> addCertification(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("titre") String titre) throws IOException {

        Enseignant enseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        // Create certifications directory if it doesn't exist
        Path uploadPath = Paths.get(CERTIFICATIONS_UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

        // Save file
        Path filePath = uploadPath.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), filePath);

        // Create certification entity
        Certification certification = new Certification();
        certification.setTitre(titre);
        certification.setFileUrl(CERTIFICATIONS_UPLOAD_DIR + uniqueFilename);
        certification.setEnseignant(enseignant);

        Certification savedCertification = certificationRepository.save(certification);
        return ResponseEntity.ok(savedCertification);
    }

    @DeleteMapping("/certifications/{certificationId}")
    public ResponseEntity<?> deleteCertification(@PathVariable Long certificationId) {
        Certification certification = certificationRepository.findById(certificationId)
                .orElseThrow(() -> new EntityNotFoundException("Certification not found"));

        // Delete file if it exists
        try {
            Path filePath = Paths.get(certification.getFileUrl());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Log error but continue with entity deletion
            e.printStackTrace();
        }

        certificationRepository.deleteById(certificationId);
        return ResponseEntity.ok().build();
    }
}