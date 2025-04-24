package com.iset.spring_integration.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class PendingRecruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Developpeur getDeveloppeur() {
        return developpeur;
    }

    public void setDeveloppeur(Developpeur developpeur) {
        this.developpeur = developpeur;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public String getTestLanguage() {
        return testLanguage;
    }

    public void setTestLanguage(String testLanguage) {
        this.testLanguage = testLanguage;
    }

    public Integer getTestScore() {
        return testScore;
    }

    public void setTestScore(Integer testScore) {
        this.testScore = testScore;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }
}
