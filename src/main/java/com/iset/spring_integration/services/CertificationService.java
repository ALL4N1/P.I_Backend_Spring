package com.iset.spring_integration.services;

import com.iset.spring_integration.entities.Certification;
import com.iset.spring_integration.repositories.CertificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CertificationService {

    @Autowired
    private CertificationRepository certificationRepository;

    public List<Certification> findByEnseignantId(Long enseignantId) {
        return certificationRepository.findByEnseignantId(enseignantId);
    }

    public Certification save(Certification certification) {
        return certificationRepository.save(certification);
    }

    public void deleteById(Long id) {
        certificationRepository.deleteById(id);
    }

    public Certification findById(Long id) {
        return certificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certification not found with id: " + id));
    }

    public boolean deleteFile(String fileUrl) {
        try {
            Path filePath = Paths.get(fileUrl);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}