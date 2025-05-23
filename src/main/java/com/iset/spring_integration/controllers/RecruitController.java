package com.iset.spring_integration.controllers;

import com.iset.spring_integration.dto.AdminPendingRecruitDTO;
import com.iset.spring_integration.dto.StatusUpdateRequest;
import com.iset.spring_integration.entities.Developpeur;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.entities.PendingRecruit;
import com.iset.spring_integration.dto.RecruitRequestDTO;
import com.iset.spring_integration.repositories.DeveloppeurRepository;
import com.iset.spring_integration.repositories.EnseignantRepository;
import com.iset.spring_integration.repositories.PendingRecruitRepository;
import com.iset.spring_integration.security.JwtService;
import com.iset.spring_integration.services.RecruitService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/recruitments")/// /

public class RecruitController {

    private final RecruitService recruitService;
    private final PendingRecruitRepository pendingRecruitRepository;
    private final JwtService jwtService;
    private final DeveloppeurRepository developpeurRepository;
    private final EnseignantRepository enseignantRepository;



    public RecruitController(RecruitService recruitService, PendingRecruitRepository pendingRecruitRepository, DeveloppeurRepository developpeurRepository, EnseignantRepository enseignantRepository) {
        this.recruitService = recruitService;
        this.pendingRecruitRepository = pendingRecruitRepository;
        this.jwtService = new JwtService();
        this.developpeurRepository = developpeurRepository;
        this.enseignantRepository = enseignantRepository;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> submitRecruitment(
            @ModelAttribute RecruitRequestDTO request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        try {
            PendingRecruit result = recruitService.createRecruitment(request, userDetails.getUsername());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/pending")
    public List<AdminPendingRecruitDTO> getPendingRecruitments() {
        return pendingRecruitRepository.findAllWithDeveloperAndTest();
    }

    @GetMapping("/uploads/cvs/{filename:.+}")
    public ResponseEntity<Resource> getCV(
            @PathVariable String filename,
            @RequestParam String token) {

        // Valider le token ici
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Resource file = new FileSystemResource("uploads/cvs/" + filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    // RecruitmentController.java
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest request) {

        recruitService.updateStatus(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteRecruitment(@PathVariable Long id) {
        recruitService.deleteRecruitment(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/promote")
    public ResponseEntity<Enseignant> promote(@RequestBody Long id) {
        Developpeur dev = developpeurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dev not found"));

        Enseignant ens = new Enseignant();
        ens.setTel(dev.getTel());
        ens.setEmail(dev.getEmail());
        ens.setNom(dev.getNom());
        ens.setPfp_url(dev.getPfp_url());
        ens.setMdp(dev.getMdp());

        Set<String> badges = new HashSet<>();
        List<PendingRecruit> recruits = pendingRecruitRepository.findAllByDeveloper(dev);
        for(PendingRecruit recruit : recruits) {
            if(recruit.getTestLanguage() != null) {
                badges.add(recruit.getTestLanguage().toLowerCase());
            }
        }
        ens.setBadges(badges);

        // Modification clé ici
        final Enseignant savedEns = enseignantRepository.save(ens);



        recruits.forEach(recruit -> recruit.setDeveloper(savedEns));
        pendingRecruitRepository.saveAll(recruits);

        developpeurRepository.delete(dev);

        return ResponseEntity.ok(savedEns);
    }

    @PatchMapping("/enseignants/{id}/badges")
    public ResponseEntity<Void> updateBadges(
            @PathVariable Long id,
            @RequestBody Set<String> newBadges) {

        recruitService.updateBadges(id, newBadges);
        return ResponseEntity.ok().build();
    }

}
