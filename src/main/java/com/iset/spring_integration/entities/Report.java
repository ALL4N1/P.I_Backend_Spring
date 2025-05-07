package com.iset.spring_integration.entities;

import com.iset.spring_integration.entities.ReportStatus;
import com.iset.spring_integration.entities.ReportType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private Developpeur reporter; // Celui qui fait le signalement

    @ManyToOne
    @JoinColumn(name = "reported_id", nullable = false)
    private Developpeur reported;

    @Column(nullable = false)
    private String reason; // Détails du problème signalé

    @Column(nullable = false)
    private String details;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @Column(nullable = false)
    private Long reportTypeId;

    @Enumerated(EnumType.STRING)
    private ReportStatus status = ReportStatus.PENDING; // Statut du signalement (ex. : En attente, Résolu)

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}

