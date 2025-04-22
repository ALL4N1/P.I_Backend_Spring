package com.iset.spring_integration.controllers;

import com.iset.spring_integration.entities.Utilisateur;
import com.iset.spring_integration.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UtilisateurController {
    @Autowired
    private UtilisateurService utilisateurService;

    @PutMapping("/{userId}/status")
    public ResponseEntity<Void> changeStatus(@PathVariable Long userId, @RequestBody String status) {
        utilisateurService.changeStatus(userId, status);
        return ResponseEntity.ok().build();
    }
}
