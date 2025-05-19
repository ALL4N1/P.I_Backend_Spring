package com.iset.spring_integration.controllers;

import com.iset.spring_integration.dto.UtilisateurChatDTO;
import com.iset.spring_integration.repositories.EnseignantRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enseignants")
public class EnseignantController {

    private final EnseignantRepository enseignantRepository;

    public EnseignantController(EnseignantRepository enseignantRepository) {
        this.enseignantRepository = enseignantRepository;
    }

    @GetMapping("/list")
    public List<UtilisateurChatDTO> getAllEnseignants() {
        return enseignantRepository.findAll()
                .stream()
                .map(e -> {
                    UtilisateurChatDTO dto = new UtilisateurChatDTO();
                    dto.setId(e.getId());
                    dto.setNom(e.getNom());
                    dto.setEmail(e.getEmail());
                    dto.setPfp_url(e.getPfp_url());
                    dto.setBadges(e.getBadges());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
