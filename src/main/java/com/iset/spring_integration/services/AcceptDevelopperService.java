package com.iset.spring_integration.services;

import com.iset.spring_integration.entities.Developpeur;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.entities.PendingRecruit;
import com.iset.spring_integration.repositories.DeveloppeurRepository;
import com.iset.spring_integration.repositories.EnseignantRepository;
import com.iset.spring_integration.repositories.PendingRecruitRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.iset.spring_integration.entities.RecruitStatus.ACCEPTED;

@Service
@Transactional
public class AcceptDevelopperService {
    @Autowired
    private DeveloppeurRepository developpeurRepository;

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private PendingRecruitRepository recruitmentRepository;

    public void promouvoirEtSupprimer(Long recruitmentId, String langageTest) {
        PendingRecruit recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new EntityNotFoundException("Recrutement non trouvé"));

        Developpeur dev = recruitment.getDeveloper();
        String nom=dev.getNom();
        String email=dev.getEmail();
        String mdp=dev.getMdp();
        int tel=dev.getTel();
        String pfp=dev.getPfp_url();
        // Suppression du développeur
        developpeurRepository.delete(dev);



        // Création du nouvel enseignant
        Enseignant enseignant = new Enseignant();
        enseignant.setNom(nom);
        enseignant.setEmail(email);
        enseignant.setMdp(mdp);
        enseignant.setTel(tel);
        enseignant.setPfp_url(pfp);
        enseignant.getBadges().add(langageTest);

        // Sauvegarde avant suppression
        enseignantRepository.save(enseignant);

        // Suppression du développeur
        developpeurRepository.delete(dev);

        // Mise à jour du recrutement
        recruitment.setStatus(ACCEPTED);
        recruitmentRepository.save(recruitment);
    }
}
