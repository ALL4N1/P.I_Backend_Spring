package com.iset.spring_integration.controllers;

import com.iset.spring_integration.dto.CahpitreDTO;
import com.iset.spring_integration.dto.CoursDTO;
import com.iset.spring_integration.entities.Chapitre;
import com.iset.spring_integration.entities.Cours;
import com.iset.spring_integration.entities.Developpeur;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.repositories.DeveloppeurRepository;
import com.iset.spring_integration.repositories.EnseignantRepository;
import com.iset.spring_integration.services.CoursService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cours")
public class CoursController {
    @Autowired
    private CoursService coursService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCours(@PathVariable Long id) {
        coursService.deleteCours(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Cours>> findAllCours() {
        return ResponseEntity.ok(coursService.findAllCours());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCours(@RequestBody CoursDTO request) {
        return new ResponseEntity<>(coursService.addCours(request),HttpStatus.CREATED);
    }

    @PostMapping("/add/chapitre")
    public ResponseEntity<?> addChapitre(@RequestBody CahpitreDTO request) {
        return new ResponseEntity<>(coursService.addChapitre(request),HttpStatus.CREATED);
    }

    @GetMapping("/{id}/chapitres")
    public ResponseEntity<?> findChapitreByCours(@PathVariable Long id) {
        return ResponseEntity.ok(coursService.findChapitreByCours(id));
    }

    @GetMapping("/subjects")
    public ResponseEntity<?> findAllSubjects() {
        return ResponseEntity.ok(coursService.getAllSubjects());
    }
}
