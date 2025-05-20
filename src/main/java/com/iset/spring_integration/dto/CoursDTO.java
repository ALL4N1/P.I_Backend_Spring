package com.iset.spring_integration.dto;

import com.iset.spring_integration.entities.Developpeur;

public class CoursDTO {
        private String titre;
        private String contenu;
        private Long enseignant_id;
        private String subject;

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

    public void setEnseignant_id(Long enseignant_id) {
        this.enseignant_id = enseignant_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
