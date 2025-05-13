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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id", nullable = false)
    private Developpeur developer;

    @Temporal(TemporalType.TIMESTAMP)
    private Date submitDate;

    private String testLanguage;
    private Double testScore; // Score sur 20

    private String cvUrl;

    @Enumerated(EnumType.STRING)
    private RecruitStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Developpeur getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developpeur developer) {
        this.developer = developer;
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

    public Double getTestScore() {
        return testScore;
    }

    public void setTestScore(Double testScore) {
        this.testScore = testScore;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public RecruitStatus getStatus() {
        return status;
    }

    public void setStatus(RecruitStatus status) {
        this.status = status;
    }
}

