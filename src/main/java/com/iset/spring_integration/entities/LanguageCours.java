package com.iset.spring_integration.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class LanguageCours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nom;


    public LanguageCours() {

    }

    @OneToMany(mappedBy = "language")
    private List<Cours> cours;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Cours> getCours() {
        return cours;
    }

    public void setCours(List<Cours> cours) {
        this.cours = cours;
    }
// Getters & Setters
}
