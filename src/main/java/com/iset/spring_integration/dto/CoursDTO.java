package com.iset.spring_integration.dto;

import com.iset.spring_integration.entities.Developpeur;
import org.springframework.web.multipart.MultipartFile;

public class CoursDTO {
        private String titre;
        private String contenu;
        private Long enseignant_id;
        private String subject;
        private MultipartFile image;

    public void setEnseignant_id(Long enseignant_id) {
        this.enseignant_id = enseignant_id;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Long getEnseignant_id() {
        return enseignant_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "CoursDTO{" +
                "titre='" + titre + '\'' +
                ", contenu='" + contenu + '\'' +
                ", enseignant_id=" + enseignant_id +
                ", subject='" + subject + '\'' +
                ", image=" + image +
                '}';
    }
}
