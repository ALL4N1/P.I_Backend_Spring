package com.iset.spring_integration.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String contenu;

    // private String image_url

    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    private Developpeur enseignant;

    @Column(name = "subject", nullable = false)
    private String subject;

    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL)
    private List<Chapitre> chapitres;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Developpeur getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Developpeur enseignant) {
        this.enseignant = enseignant;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Chapitre> getChapitres() {
        return chapitres;
    }

    public void setChapitres(List<Chapitre> chapitres) {
        this.chapitres = chapitres;
    }
}
