package com.iset.spring_integration.controllers;

import com.iset.spring_integration.dto.CertificationDTO;
import com.iset.spring_integration.entities.Certification;
import com.iset.spring_integration.services.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "*")
public class FileUploadController {

    private final String CERTIFICATION_UPLOAD_DIR = "uploads/certifications";

    @Autowired
    private CertificationService certificationService;

    @PostMapping("/certification")
    public ResponseEntity<?> uploadCertification(
            @RequestParam("file") MultipartFile file,
            @RequestParam("titre") String titre,
            @RequestParam("enseignant_id") Long enseignantId) {

        try {
            // Create directories if they don't exist
            Path uploadPath = Paths.get(CERTIFICATION_UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate a unique filename with original extension
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = enseignantId + "_" + UUID.randomUUID().toString() + fileExtension;

            // Save the file
            Path filePath = uploadPath.resolve(newFilename);
            Files.copy(file.getInputStream(), filePath);

            // Create and save certification
            CertificationDTO certificationDTO = new CertificationDTO();
            certificationDTO.setTitre(titre);
            certificationDTO.setFileUrl(CERTIFICATION_UPLOAD_DIR + "/" + newFilename);
            certificationDTO.setEnseignant_id(enseignantId);

            Certification savedCertification = certificationService.addCertification(certificationDTO);

            // Build response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Certification uploaded successfully");
            response.put("certification", Map.of(
                    "id", savedCertification.getId(),
                    "titre", savedCertification.getTitre(),
                    "fileUrl", savedCertification.getFileUrl()
            ));

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to upload file: " + e.getMessage()));
        }
    }
}