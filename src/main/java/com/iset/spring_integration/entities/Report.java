package com.iset.spring_integration.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "developpeur_id", nullable = false)
    private Developpeur developpeur; // Celui qui fait le signalement

    @ManyToOne
    @JoinColumn(name = "cours_id", nullable = false)
    private Cours cours; // Le cours concerné par le problème

    private String description; // Détails du problème signalé

    @Enumerated(EnumType.STRING)
    private ReportStatus status; // Statut du signalement (ex. : En attente, Résolu)

    private LocalDateTime dateCreation; // Date du signalement

    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now(); // Définir la date au moment de la création
        this.status = ReportStatus.PENDING; // Par défaut, le statut est "En attente"
    }


}

