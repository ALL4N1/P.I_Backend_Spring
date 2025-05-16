package com.iset.spring_integration.dto;

import com.iset.spring_integration.entities.QuestionDifficulty;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String contenu;
    private QuestionDifficulty difficulty;
    private String bonneReponse;
    private String topic;
    private String reponseA;
    private String reponseB;
    private String reponseC;
    private String reponseD;
    private String explanation;
    private Integer timeLimit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public QuestionDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(QuestionDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getBonneReponse() {
        return bonneReponse;
    }

    public void setBonneReponse(String bonneReponse) {
        this.bonneReponse = bonneReponse;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getReponseA() {
        return reponseA;
    }

    public void setReponseA(String reponseA) {
        this.reponseA = reponseA;
    }

    public String getReponseB() {
        return reponseB;
    }

    public void setReponseB(String reponseB) {
        this.reponseB = reponseB;
    }

    public String getReponseC() {
        return reponseC;
    }

    public void setReponseC(String reponseC) {
        this.reponseC = reponseC;
    }

    public String getReponseD() {
        return reponseD;
    }

    public void setReponseD(String reponseD) {
        this.reponseD = reponseD;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }
}
