package com.iset.spring_integration.dto;

import com.iset.spring_integration.entities.TypeChapitre;

public class CahpitreDTO {
    private Long id_cours;
    private Integer placement;
    private String titre, url_chapitre;
    private TypeChapitre type_chapitre;

    public Long getId_cours() {
        return id_cours;
    }

    public void setId_cours(Long id_cours) {
        this.id_cours = id_cours;
    }

    public Integer getPlacement() {
        return placement;
    }

    public void setPlacement(Integer placement) {
        this.placement = placement;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getUrl_chapitre() {
        return url_chapitre;
    }

    public void setUrl_chapitre(String url_chapitre) {
        this.url_chapitre = url_chapitre;
    }

    public TypeChapitre getType_chapitre() {
        return type_chapitre;
    }

    public void setType_chapitre(TypeChapitre type_chapitre) {
        this.type_chapitre = type_chapitre;
    }
}
