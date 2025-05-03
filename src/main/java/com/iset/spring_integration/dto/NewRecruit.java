package com.iset.spring_integration.dto;

public class NewRecruit {
    private Long idDev;
    private String cvUrl, testLanguage;
    private Integer testScore;

    public Long getIdDev() {
        return idDev;
    }

    public void setIdDev(Long idDev) {
        this.idDev = idDev;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
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
}
