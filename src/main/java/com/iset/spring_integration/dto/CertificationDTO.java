package com.iset.spring_integration.dto;

import com.iset.spring_integration.entities.Certification;

public class CertificationDTO {
    private Long id;
    private String titre;
    private String fileUrl;

    public CertificationDTO() {
    }

    public CertificationDTO(Certification certification) {
        this.id = certification.getId();
        this.titre = certification.getTitre();
        this.fileUrl = certification.getFileUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}