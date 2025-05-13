package com.iset.spring_integration.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;
import com.iset.spring_integration.entities.QuestionDifficulty;

@Data
public class TestDTO {
    private Long id;
    private String title;
    private String language;
    private String description;
    private Integer duration;
    private Map<QuestionDifficulty, Integer> difficultyLevels;
    private List<QuestionDTO> questions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Map<QuestionDifficulty, Integer> getDifficultyLevels() {
        return difficultyLevels;
    }

    public void setDifficultyLevels(Map<QuestionDifficulty, Integer> difficultyLevels) {
        this.difficultyLevels = difficultyLevels;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}
