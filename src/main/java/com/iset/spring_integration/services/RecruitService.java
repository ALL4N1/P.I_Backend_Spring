package com.iset.spring_integration.services;

import com.iset.spring_integration.dto.RecruitRequestDTO;
import com.iset.spring_integration.entities.*;
import com.iset.spring_integration.repositories.DeveloppeurRepository;
import com.iset.spring_integration.repositories.PendingRecruitRepository;
import com.iset.spring_integration.repositories.TestRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RecruitService {
    private final PendingRecruitRepository pendingRecruitRepository;
    private final TestRepository testRepository;
    private final DeveloppeurRepository developpeurRepository;
    private final FileStorageService fileStorageService;

    public RecruitService(PendingRecruitRepository pendingRecruitRepository, TestRepository testRepository, DeveloppeurRepository developpeurRepository, FileStorageService fileStorageService) {
        this.pendingRecruitRepository = pendingRecruitRepository;
        this.testRepository = testRepository;
        this.developpeurRepository = developpeurRepository;
        this.fileStorageService = fileStorageService;
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
}