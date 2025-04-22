package com.iset.spring_integration.controllers;

import com.iset.spring_integration.entities.Developpeur;
import com.iset.spring_integration.services.DeveloppeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/developpeurs")
public class DeveloppeurController {

    @Autowired
    private DeveloppeurService developpeurService;

    // Approuver un développeur (dev → professionnel)
    @PutMapping("/{id}/approve")
    public ResponseEntity<Void> approveDeveloper(@PathVariable Long id) {
        developpeurService.approveDeveloper(id);
        return ResponseEntity.ok().build();
    }

    // Rétrograder un développeur (pro → dev)
    @PutMapping("/{id}/demote")
    public ResponseEntity<Void> demoteDeveloper(@PathVariable Long id) {
        developpeurService.demoteDeveloper(id);
        return ResponseEntity.ok().build();
    }

    // Supprimer un développeur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeveloper(@PathVariable Long id) {
        developpeurService.deleteDeveloper(id);
        return ResponseEntity.noContent().build();
    }
}

