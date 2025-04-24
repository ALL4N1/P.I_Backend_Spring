package com.iset.spring_integration.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iset.spring_integration.util.ResponsesJSON;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Map;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String contenu;

    @Column(nullable = false)
    private String bonneReponse;

    @Column(columnDefinition = "json", nullable = true)
    @Convert(converter = ResponsesJSON.class)
    private Map<Integer, String> reponses = null;



    public Long getId() {
        return id;
    }

    public String getContenu() {
        return contenu;
    }

    public String getBonneReponse() {
        return bonneReponse;
    }

    public Map<Integer, String> getReponses() {
        return reponses;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setBonneReponse(String bonneReponseIndex) {
        this.bonneReponse = bonneReponseIndex;
    }

    public void setReponses(Map<Integer, String> reponses) {
        this.reponses = reponses;
    }
}
