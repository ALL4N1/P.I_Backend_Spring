package com.iset.spring_integration.dto;

import com.iset.spring_integration.entities.PendingRecruit;
import lombok.Data;

import java.beans.ConstructorProperties;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
public class AdminPendingRecruitDTO {
    private Long id;
    private String developerName;
    private String developerImage;
    private String developerEmail;
    private String testTitle;
    private String testLanguage;
    private Double testScore;
    private String status;
    private String formattedSubmitDate;
    private String cvUrl;
    private Long developerId; // Ajoutez ce cham

    @ConstructorProperties({
            "id",
            "developerName",
            "developerImage",
            "developerEmail",
            "testTitle",
            "testLanguage",
            "testScore",
            "status",
            "formattedSubmitDate",
            "cvUrl",
            "developerId"
    })
    public AdminPendingRecruitDTO(Long id, String developerName, String developerImage, String developerEmail, String testTitle, String testLanguage, Double testScore, String status, String formattedSubmitDate, String cvUrl, Long developerId) {
        this.id = id;
        this.developerName = developerName;
        this.developerImage = developerImage;
        this.developerEmail = developerEmail;
        this.testTitle = testTitle;
        this.testLanguage = testLanguage;
        this.testScore = testScore;
        this.status = status;
        this.formattedSubmitDate = formattedSubmitDate;
        this.cvUrl = cvUrl;
        this.developerId = developerId;
    }

    public Long getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Long developerId) {
        this.developerId = developerId;
    }

    public AdminPendingRecruitDTO() {
        // Constructeur par défaut nécessaire pour la désérialisation
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public String getDeveloperImage() {
        return developerImage;
    }

    public void setDeveloperImage(String developerImage) {
        this.developerImage = developerImage;
    }

    public String getDeveloperEmail() {
        return developerEmail;
    }

    public void setDeveloperEmail(String developerEmail) {
        this.developerEmail = developerEmail;
    }

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFormattedSubmitDate() {
        return formattedSubmitDate;
    }

    public void setFormattedSubmitDate(String formattedSubmitDate) {
        this.formattedSubmitDate = formattedSubmitDate;
    }

    public AdminPendingRecruitDTO(PendingRecruit recruit) {
        this.id = recruit.getId();
        this.developerName = recruit.getDeveloper().getNom();
        this.developerImage = recruit.getDeveloper().getPfp_url();
        this.developerEmail = recruit.getDeveloper().getEmail();
        this.testTitle = recruit.getTest().getTitle();
        this.testLanguage = recruit.getTestLanguage();
        this.testScore = recruit.getTestScore();
        this.status = recruit.getStatus().name();
        this.formattedSubmitDate = formatDate(recruit.getSubmitDate());
        this.cvUrl= recruit.getCvUrl();
    }

    private String formatDate(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .format(formatter);
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }
}