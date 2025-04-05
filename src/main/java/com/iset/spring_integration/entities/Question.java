package com.iset.spring_integration.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenu;
    private Integer bonneReponseIndex;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private TestRecrutement testRecrutement;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Reponse> reponses; // Liste des choix de réponses

    public Integer getBonneReponseIndex() {
        return bonneReponseIndex;
    }

    public void setBonneReponseIndex(Integer bonneReponseIndex) {
        this.bonneReponseIndex = bonneReponseIndex;
    }

    public TestRecrutement getTest() {
        return testRecrutement;
    }

    public void setTest(TestRecrutement testRecrutement) {
        this.testRecrutement = testRecrutement;
    }

    public List<Reponse> getReponses() {
        return reponses;
    }

    public void setReponses(List<Reponse> reponses) {
        this.reponses = reponses;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
