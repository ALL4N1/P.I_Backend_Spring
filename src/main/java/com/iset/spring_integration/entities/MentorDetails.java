package com.iset.spring_integration.entities;

import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Embeddable
public class MentorDetails {
    private String description;

    @ElementCollection
    @CollectionTable(name = "mentor_requirements", joinColumns = @JoinColumn(name = "test_id"))
    @Column(name = "requirement")
    private List<String> requirements = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "mentor_benefits", joinColumns = @JoinColumn(name = "test_id"))
    @Column(name = "benefit")
    private List<String> benefits = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<String> requirements) {
        this.requirements = requirements;
    }

    public List<String> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }
}