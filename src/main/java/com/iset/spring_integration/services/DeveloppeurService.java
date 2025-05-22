package com.iset.spring_integration.services;

import com.iset.spring_integration.dto.DeveloppeurDTO;
import com.iset.spring_integration.entities.Cours;
import com.iset.spring_integration.entities.Developpeur;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.repositories.CoursRepository;
import com.iset.spring_integration.repositories.DeveloppeurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeveloppeurService {
    @Autowired
    private DeveloppeurRepository devRepository;

    @Autowired
    private CoursRepository coursRepository;


    public List<Enseignant> findAllTeachers() {
        return devRepository.findAll().stream()
                .filter(dev -> dev instanceof Enseignant)
                .map(dev -> (Enseignant) dev)
                .collect(Collectors.toList());
    }

    public List<DeveloppeurDTO> getAllDeveloppeurs() {
        return devRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void deleteDeveloppeur(Long id) {
        Developpeur developpeur = devRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Développeur non trouvé avec l'id : " + id));
        devRepository.delete(developpeur);
    }

    private DeveloppeurDTO convertToDTO(Developpeur developpeur) {
        DeveloppeurDTO dto = new DeveloppeurDTO();
        dto.setId(developpeur.getId());
        dto.setNom(developpeur.getNom());
        dto.setEmail(developpeur.getEmail());
        dto.setPfp_url(developpeur.getPfp_url());
        dto.setTel(developpeur.getTel());
        return dto;
    }





}
