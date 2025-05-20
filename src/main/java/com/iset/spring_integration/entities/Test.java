package com.iset.spring_integration.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@SequenceGenerator(name = "test_seq", initialValue = 1, allocationSize = 1)
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String language;
    private String description;
    private Integer duration; // en minutes

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "test_difficulty_levels", joinColumns = @JoinColumn(name = "test_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "difficulty_level")
    @Column(name = "question_count")
    private Map<QuestionDifficulty, Integer> difficultyLevels = new EnumMap<>(QuestionDifficulty.class);

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "description",
                    column = @Column(name = "mentor_description") // Renomme la colonne
            )
    })
    private MentorDetails mentorDetails;

    public Test(Long id, String title, String language, String description, Integer duration, List<Question> questions, Map<QuestionDifficulty, Integer> difficultyLevels, MentorDetails mentorDetails) {
        this.id = id;
        this.title = title;
        this.language = language;
        this.description = description;
        this.duration = duration;
        this.questions = questions;
        this.difficultyLevels = difficultyLevels;
        this.mentorDetails = mentorDetails;
    }
    public Test(){};

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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Map<QuestionDifficulty, Integer> getDifficultyLevels() {
        return difficultyLevels;
    }

    public void setDifficultyLevels(Map<QuestionDifficulty, Integer> difficultyLevels) {
        this.difficultyLevels = difficultyLevels;
    }

    public MentorDetails getMentorDetails() {
        return mentorDetails;
    }

    public void setMentorDetails(MentorDetails mentorDetails) {
        this.mentorDetails = mentorDetails;
    }
}

