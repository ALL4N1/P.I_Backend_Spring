package com.iset.spring_integration.dto;

import com.iset.spring_integration.entities.Certification;

public class CertificationDTO {
    private Long id;
    private String titre;
    private String fileUrl;
    private Long enseignant_id;


    public CertificationDTO() {
    }

    public CertificationDTO(Certification certification) {
        this.id = certification.getId();
        this.titre = certification.getTitre();
        this.fileUrl = certification.getFileUrl();
        this.enseignant_id = certification.getEnseignant().getId();
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
    public Long getEnseignant_id() {
        return enseignant_id;
    }
    public void setEnseignant_id(Long enseignant_id) {
        this.enseignant_id = enseignant_id;
    }
}