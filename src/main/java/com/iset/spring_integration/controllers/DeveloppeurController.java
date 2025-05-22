package com.iset.spring_integration.controllers;

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

    @GetMapping("/list")
    public ResponseEntity<List<Developpeur>> getDevelopersList(){
        return ResponseEntity.ok(devService.findAll());
    }



}
