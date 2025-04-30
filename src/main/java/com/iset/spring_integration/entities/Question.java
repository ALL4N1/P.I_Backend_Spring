package com.iset.spring_integration.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iset.spring_integration.util.ResponsesJSON;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Map;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private QuestionDifficulty difficulty;

    @Column(unique = true, nullable = false)
    private String contenu;

    @Column(nullable = false)
    private String bonneReponse;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    @Convert(converter = ResponsesJSON.class)
    private Map<Integer, String> reponses = null;

    @Column(nullable = false)
    private String topic;


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

    public QuestionDifficulty getDifficulty() { return difficulty; }

    public String getTopic() { return topic; }

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

    public void setDifficulty(QuestionDifficulty difficulty) { this.difficulty = difficulty; }

    public void setTopic(String topic) { this.topic = topic; }
}
