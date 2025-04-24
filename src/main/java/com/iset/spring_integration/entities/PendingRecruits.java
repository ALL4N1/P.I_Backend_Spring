package com.iset.spring_integration.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class PendingRecruits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name= "recruit_id")
    private Developpeur developpeur;

    @Column(nullable = false)
    private Date submitDate;

    @Column(nullable = false)
    private String testLanguage;

    @Column(nullable = false)
    private Integer testScore;

    @Column(nullable = false)
    private String cvUrl;

    @PrePersist
    protected void onCreate() {
        this.submitDate = new Date(); // Définir la date au moment de la création
    }

}
