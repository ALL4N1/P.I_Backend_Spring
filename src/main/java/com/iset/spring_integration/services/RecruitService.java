package com.iset.spring_integration.services;

import com.iset.spring_integration.dto.RecruitRequestDTO;
import com.iset.spring_integration.dto.StatusUpdateRequest;
import com.iset.spring_integration.entities.*;
import com.iset.spring_integration.repositories.DeveloppeurRepository;
import com.iset.spring_integration.repositories.EnseignantRepository;
import com.iset.spring_integration.repositories.PendingRecruitRepository;
import com.iset.spring_integration.repositories.TestRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class RecruitService {
    private final PendingRecruitRepository pendingRecruitRepository;
    private final TestRepository testRepository;
    private final DeveloppeurRepository developpeurRepository;
    private final FileStorageService fileStorageService;
    private final EnseignantRepository enseignantRepository;


    public RecruitService(PendingRecruitRepository pendingRecruitRepository,EnseignantRepository enseignantRepository, TestRepository testRepository, DeveloppeurRepository developpeurRepository, FileStorageService fileStorageService ) {
        this.pendingRecruitRepository = pendingRecruitRepository;
        this.testRepository = testRepository;
        this.developpeurRepository = developpeurRepository;
        this.fileStorageService = fileStorageService;
        this.enseignantRepository = enseignantRepository;
    }

    @Transactional
    public PendingRecruit createRecruitment(RecruitRequestDTO request, String username) {
        Developpeur developer = developpeurRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Développeur non trouvé"));

        Test test = testRepository.findById(request.getTestId())
                .orElseThrow(() -> new RuntimeException("Test non trouvé"));

        String cvPath = fileStorageService.store(request.getCv());

        return pendingRecruitRepository.save(
                new PendingRecruit(
                        test,
                        developer,
                        new Date(),
                        test.getLanguage(), // Récupération directe depuis Test
                        request.getScore(),
                        cvPath,
                        RecruitStatus.PENDING
                )
        );
    }

    @Transactional
    public void updateStatus(Long id, StatusUpdateRequest request) {
        PendingRecruit recruit = pendingRecruitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidature non trouvée"));

        // Mise à jour du statut
        recruit.setStatus(request.getStatus());
        pendingRecruitRepository.save(recruit);



    }
    @Transactional
    public void deleteRecruitment(Long id) {
        pendingRecruitRepository.deleteById(id);
    }

    @Transactional
    public void updateBadges(Long enseignantId, Set<String> newBadges) {
        Enseignant ens = enseignantRepository.findById(enseignantId)
                .orElseThrow(() -> new EntityNotFoundException("Enseignant non trouvé"));

        Set<String> currentBadges = ens.getBadges();
        newBadges.forEach(badge -> {
            String cleanBadge = badge.trim().toLowerCase();
            if (!currentBadges.contains(cleanBadge)) {
                currentBadges.add(cleanBadge);
            }
        });

        enseignantRepository.save(ens);
    }


}