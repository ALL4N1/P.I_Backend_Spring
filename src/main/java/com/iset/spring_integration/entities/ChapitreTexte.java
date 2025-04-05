package com.iset.spring_integration.entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("TEXTE")
public class ChapitreTexte extends Chapitre {
    @Column(columnDefinition = "TEXT")
    private String contenu;

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
// Getters & Setters
}

