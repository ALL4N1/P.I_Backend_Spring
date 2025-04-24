package com.iset.spring_integration.entities;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class Chapitre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private String urlChapitre;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeChapitre typeChapitre;

    @ManyToOne
    @JoinColumn(name = "cours_id", nullable = false)
    private Cours cours;

}

