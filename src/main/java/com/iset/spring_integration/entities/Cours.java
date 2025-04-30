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

    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;

    @Column(name = "subject", nullable = false)
    private String subject;

    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL)
    private List<Chapitre> chapitres;
}
