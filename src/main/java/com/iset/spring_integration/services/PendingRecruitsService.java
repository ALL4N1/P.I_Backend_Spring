package com.iset.spring_integration.services;

import com.iset.spring_integration.entities.Developpeur;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.entities.PendingRecruit;
import com.iset.spring_integration.repositories.DeveloppeurRepository;
import com.iset.spring_integration.repositories.RecruitsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PendingRecruitsService {
    @Autowired
    private RecruitsRepository recruitsRepo;

    @Autowired
    private DeveloppeurRepository devRepository;

    public Developpeur approveApp(Long id) {
        PendingRecruit recruit = recruitsRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Recruitement non trouvée"));
        Developpeur dev = recruit.getDeveloppeur();
        if(!(dev instanceof Enseignant)){
            Enseignant enseignant = new Enseignant();
            enseignant.setId(id);
            enseignant.getBadges().add(recruit.getTestLanguage());
            enseignant.setEmail(dev.getEmail());
            enseignant.setNom(dev.getNom());
            enseignant.setTel(dev.getTel());
            enseignant.setMdp(dev.getMdp());
            enseignant.setPfp_url(dev.getPfp_url());
            enseignant.setIsBanned(dev.getIsBanned());
            devRepository.delete(dev);
            recruitsRepo.delete(recruit);
            return devRepository.save(enseignant);
        }
        else{
            ((Enseignant) dev).getBadges().add(recruit.getTestLanguage());
            recruitsRepo.delete(recruit);
            return devRepository.save(dev);
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
}
