package com.iset.spring_integration.controllers;

import com.iset.spring_integration.dto.DeveloppeurDTO;
import com.iset.spring_integration.entities.Developpeur;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.services.DeveloppeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dev")
public class DeveloppeurController {
    @Autowired
    private DeveloppeurService devService;

    public DeveloppeurController(DeveloppeurService devService) {
        this.devService = devService;
    }
    @GetMapping
    public ResponseEntity<List<DeveloppeurDTO>> getAllDeveloppeurs() {
        List<DeveloppeurDTO> developpeurs = devService.getAllDeveloppeurs();
        return new ResponseEntity<>(developpeurs, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeveloppeur(@PathVariable Long id) {
        try {
            devService.deleteDeveloppeur(id);
            return new ResponseEntity<>("Développeur supprimé avec succès", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
