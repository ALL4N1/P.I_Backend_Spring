package com.iset.spring_integration.services;

import com.iset.spring_integration.dto.CertificationDTO;
import com.iset.spring_integration.entities.Certification;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.repositories.CertificationRepository;
import com.iset.spring_integration.repositories.EnseignantRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationService {
    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private EnseignantRepository enseignantRepository;

    public List<Certification> findAllCertifications() {
        return certificationRepository.findAll();
    }

    public void deleteCertification(Long id) {
        certificationRepository.deleteById(id);
    }

    public Certification addCertification(CertificationDTO request) {
        Enseignant enseignant = enseignantRepository.findById(request.getEnseignant_id())
                .orElseThrow(() -> new EntityNotFoundException("Enseignant not found"));

        Certification certification = new Certification();
        certification.setEnseignant(enseignant);
        certification.setTitre(request.getTitre());
        certification.setFileUrl(request.getFileUrl());

        return certificationRepository.save(certification);
    }

    public List<Certification> findCertificationsByEnseignant(Enseignant enseignant) {
        return certificationRepository.findByEnseignant(enseignant);
    }
}

