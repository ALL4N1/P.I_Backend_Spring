package com.iset.spring_integration.dto;

import com.iset.spring_integration.entities.TypeChapitre;
import org.springframework.web.multipart.MultipartFile;

public class ChapitreDTO {
    private Long id_cours;
    private Integer placement;
    private String titre;
    private MultipartFile chapitre_file;
    private TypeChapitre type_chapitre;

    public MultipartFile getChapitre_file() {
        return chapitre_file;
    }

    public void setChapitre_file(MultipartFile chapitre_file) {
        this.chapitre_file = chapitre_file;
    }

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

    public TypeChapitre getType_chapitre() {
        return type_chapitre;
    }

    public void setType_chapitre(TypeChapitre type_chapitre) {
        this.type_chapitre = type_chapitre;
    }
}
