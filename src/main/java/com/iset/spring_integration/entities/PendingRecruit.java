package com.iset.spring_integration.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PendingRecruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id", nullable = false)
    private Developpeur developer;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date submitDate;

    @Column(name = "test_language", nullable = false) // Nom de colonne explicite
    private String testLanguage;

    @Column(nullable = false)
    private Double testScore;

    @Column(name = "cv_url", nullable = false)
    private String cvUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitStatus status;


    // Constructeur personnalisé si nécessaire
    public PendingRecruit(Test test, Developpeur developer, Date submitDate,
                          String testLanguage, Double testScore, String cvUrl,
                          RecruitStatus status) {
        this.test = test;
        this.developer = developer;
        this.submitDate = submitDate;
        this.testLanguage = testLanguage;
        this.testScore = testScore;
        this.cvUrl = cvUrl;
        this.status = status;
    }
}