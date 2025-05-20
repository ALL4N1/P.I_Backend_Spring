package com.iset.spring_integration.dto;

import com.iset.spring_integration.entities.Utilisateur;

import java.util.Collections;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private final Long id;

    public CustomUserDetails(Utilisateur utilisateur) {
        super(
                utilisateur.getEmail(),
                utilisateur.getMdp(),
                Collections.emptyList() // ou vos rôles/autorisations
        );
        this.id = utilisateur.getId();
    }

    public Long getId() {
        return id;
    }
}
