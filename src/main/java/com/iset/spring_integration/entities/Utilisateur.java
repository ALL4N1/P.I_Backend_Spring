package com.iset.spring_integration.entities;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Une seule table pour tous les utilisateurs
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING) // Ajoute une colonne "role" pour distinguer les types d'utilisateurs
public abstract class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String email;

    private String pfp_url;

    @Column(nullable = false)
    private String mdp;
}
