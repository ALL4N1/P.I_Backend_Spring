package com.iset.spring_integration.dto;

import com.iset.spring_integration.entities.QuestionDifficulty;
import java.util.List;
import java.util.Map;

public class TestCreationDTO {
    private String title;

    private String language;

    private String description;

    private Integer duration;

    private Map<QuestionDifficulty, Integer> difficultyLevels;

    private List<QuestionCreationDTO> questions;

    private MentorDetailsDTO mentorDetails;

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

    public List<QuestionCreationDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionCreationDTO> questions) {
        this.questions = questions;
    }

    public MentorDetailsDTO getMentorDetails() {
        return mentorDetails;
    }

    public void setMentorDetails(MentorDetailsDTO mentorDetails) {
        this.mentorDetails = mentorDetails;
    }
}