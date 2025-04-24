package com.iset.spring_integration.services;

import com.iset.spring_integration.entities.Developpeur;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.repositories.DeveloppeurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloppeurService {
    @Autowired
    private DeveloppeurRepository devRepository;

    public List<Developpeur> findAll() {
        return devRepository.findAllDevelopersAndTeachers();
    }

    public void toggleBanDev(Long id, boolean isBanned) {
        Developpeur dev = devRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Developpeur non trouvé"));
        dev.setIsBanned(isBanned);
        devRepository.save(dev);
    }

    public Developpeur removeBadge(Long id, String badge) {
        Enseignant ens = (Enseignant) devRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Enseignant non trouvé"));
        ens.getBadges().remove(badge);
        if(ens.getBadges().isEmpty()){
            return this.demoteDev(id);
        }
        else{
            return devRepository.save(ens);
        }
    }

    public Developpeur demoteDev(Long id) {
        Enseignant ens = (Enseignant) devRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Enseignant non trouvé"));
        Developpeur dev = new Developpeur();
        dev.setId(ens.getId());
        dev.setIsBanned(ens.getIsBanned());
        dev.setEmail(ens.getEmail());
        dev.setMdp(ens.getMdp());
        dev.setNom(ens.getNom());
        dev.setPfp_url(ens.getPfp_url());
        dev.setTel(ens.getTel());
        devRepository.delete(ens);
        return devRepository.save(dev);
    }
}
