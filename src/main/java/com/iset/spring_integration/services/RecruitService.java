package com.iset.spring_integration.services;

import com.iset.spring_integration.entities.Developpeur;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.entities.PendingRecruit;
import com.iset.spring_integration.repositories.DeveloppeurRepository;
import com.iset.spring_integration.repositories.RecruitsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RecruitService {
    @Autowired
    private RecruitsRepository recruitsRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DeveloppeurRepository devRepository;

    @Transactional
    public void approveApp(Long id) {
        PendingRecruit recruit = recruitsRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recruitment not found with id: " + id));

        Developpeur dev = recruit.getDeveloppeur();

        if (!(dev instanceof Enseignant)) {
            // Create new Enseignant
            Enseignant enseignant = new Enseignant();
            enseignant.getBadges().add(recruit.getTestLanguage());
            enseignant.setEmail(dev.getEmail());
            enseignant.setNom(dev.getNom());
            enseignant.setTel(dev.getTel());
            enseignant.setMdp(dev.getMdp());
            enseignant.setPfp_url(dev.getPfp_url());
            enseignant.setIsBanned(dev.getIsBanned());
            enseignant.setWarnings(dev.getWarnings());

            // 1. Break the relationship
            recruit.setDeveloppeur(null);
            recruitsRepo.save(recruit);

            // 2. Flush to ensure it's persisted before delete
            recruitsRepo.flush();

            // 3. Now safely delete the dev
            devRepository.delete(dev);

            // 4. Save new enseignant
            devRepository.save(enseignant);

            // 5. Finally delete the recruit
            recruitsRepo.delete(recruit);
        } else {
            ((Enseignant) dev).getBadges().add(recruit.getTestLanguage());
            recruitsRepo.delete(recruit);
            devRepository.save(dev);
        }
    }

    public void rejectApp(Long id) {
        PendingRecruit recruit = recruitsRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Recruitement non trouvée"));
        recruitsRepo.delete(recruit);
    }

    public List<PendingRecruit> getPendingRecruits(){
        return recruitsRepo.findAll();
    }

    public PendingRecruit addRecruit(Long idDev, Integer testScore, String testLanguage, String cvURL) {
        Developpeur dev = devRepository.findById(idDev).orElseThrow(
                () -> new EntityNotFoundException("Developpeur not found"));
        PendingRecruit recruit = new PendingRecruit();
        recruit.setDeveloppeur(dev);
        recruit.setTestLanguage(testLanguage);
        recruit.setTestScore(testScore);
        recruit.setCvUrl(cvURL);
        recruit.setSubmitDate(new Date());
        return recruitsRepo.save(recruit);
    }
}
