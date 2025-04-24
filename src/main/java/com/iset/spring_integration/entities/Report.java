package com.iset.spring_integration.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iset.spring_integration.util.ReportContentJSON;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private Developpeur reporter; // Celui qui fait le signalement

    @ManyToOne
    @JoinColumn(name = "reported_id", nullable = false)
    private Developpeur reported; // Le cours concerné par le problème

    @Column(nullable = false)
    private String reason; // Détails du problème signalé

    @Enumerated(EnumType.STRING)
    private ReportType type;

    @Column(nullable = false)
    private String comment;

    @Column(columnDefinition = "json", nullable = true)
    @Convert(converter = ReportContentJSON.class)
    private Map<Integer, String> reportContent = null;

    @Enumerated(EnumType.STRING)
    private ReportStatus status; // Statut du signalement (ex. : En attente, Résolu)

    @PrePersist
    protected void onCreate() {
        this.status = ReportStatus.PENDING; // Par défaut, le statut est "En attente"
    }


}

