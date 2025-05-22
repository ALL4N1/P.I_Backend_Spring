package com.iset.spring_integration.dto;

import com.iset.spring_integration.entities.Projet;

public class ProjetDTO {
    private Long id;
    private String titre;
    private String description;
    private String githubLink;
    private Long enseignant_id;


    public ProjetDTO() {
    }

    public ProjetDTO(Projet projet) {
        this.id = projet.getId();
        this.titre = projet.getTitre();
        this.description = projet.getDescription();
        this.githubLink = projet.getGithubLink();
        this.enseignant_id = projet.getEnseignant().getId();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }

    public Long getEnseignant_id() {
        return enseignant_id;
    }

    public void setEnseignant_id(Long enseignant_id) {
        this.enseignant_id = enseignant_id;
    }
}
