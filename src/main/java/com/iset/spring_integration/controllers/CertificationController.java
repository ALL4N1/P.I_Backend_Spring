package com.iset.spring_integration.controllers;



import com.iset.spring_integration.dto.CertificationDTO;
import com.iset.spring_integration.entities.Certification;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.repositories.EnseignantRepository;
import com.iset.spring_integration.services.CertificationService;
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
@RequestMapping("/api/certifications")
@CrossOrigin(origins = "*")
public class CertificationController {
    @Autowired
    private CertificationService certificationService;

    @Autowired
    private EnseignantRepository enseignantRepository;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertification(@PathVariable Long id) {
        certificationService.deleteCertification(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> findAllCertifications() {
        List<Map<String, Object>> simplifiedCerts = certificationService.findAllCertifications().stream()
                .map(cert -> {
                    Map<String, Object> certMap = new HashMap<>();
                    certMap.put("id", cert.getId());
                    certMap.put("titre", cert.getTitre());
                    certMap.put("fileUrl", cert.getFileUrl());

                    if (cert.getEnseignant() != null) {
                        Map<String, Object> teacherMap = new HashMap<>();
                        teacherMap.put("id", cert.getEnseignant().getId());
                        teacherMap.put("nom", cert.getEnseignant().getNom());
                        certMap.put("enseignant", teacherMap);
                    }

                    return certMap;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(simplifiedCerts);
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addCertification(@RequestBody CertificationDTO request) {
        Certification savedCert = certificationService.addCertification(request);

        Map<String, Object> response = new HashMap<>();
        response.put("id", savedCert.getId());
        response.put("titre", savedCert.getTitre());
        response.put("fileUrl", savedCert.getFileUrl());

        if (savedCert.getEnseignant() != null) {
            Map<String, Object> teacherMap = new HashMap<>();
            teacherMap.put("id", savedCert.getEnseignant().getId());
            teacherMap.put("nom", savedCert.getEnseignant().getNom());
            response.put("enseignant", teacherMap);
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/enseignant/{id}")
    public ResponseEntity<?> getCertificationsByEnseignant(@PathVariable Long id) {
        try {
            Enseignant enseignant = enseignantRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Enseignant not found"));

            List<Map<String, Object>> simplifiedCerts = certificationService.findCertificationsByEnseignant(enseignant).stream()
                    .map(cert -> {
                        Map<String, Object> certMap = new HashMap<>();
                        certMap.put("id", cert.getId());
                        certMap.put("titre", cert.getTitre());
                        certMap.put("fileUrl", cert.getFileUrl());
                        return certMap;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(simplifiedCerts);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}