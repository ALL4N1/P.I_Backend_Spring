package com.iset.spring_integration.entities;

import com.iset.spring_integration.util.StringSetConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("ENSEIGNANT")
public class Enseignant extends Developpeur {
<<<<<<< HEAD
    @Column(columnDefinition = "TEXT")
    private String bio;

    private String position;

    private String university;

    @OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL)
    private List<Cours> coursList = new ArrayList<>();

    @OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL)
    private List<Projet> projets = new ArrayList<>();

    @OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL)
    private List<Certification> certifications = new ArrayList<>();

=======
>>>>>>> d3e42cd97f077134d488c0b5c2d582102ccb9941
    @Column(name = "badges", columnDefinition = "TEXT")
    @Convert(converter = StringSetConverter.class)
    private Set<String> badges = new HashSet<>();

<<<<<<< HEAD
    // Getters and Setters
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public List<Cours> getCoursList() {
        return coursList;
    }

    public void setCoursList(List<Cours> coursList) {
        this.coursList = coursList;
    }

    public List<Projet> getProjets() {
        return projets;
    }

    public void setProjets(List<Projet> projets) {
        this.projets = projets;
    }

    public List<Certification> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<Certification> certifications) {
        this.certifications = certifications;
    }

=======
>>>>>>> d3e42cd97f077134d488c0b5c2d582102ccb9941
    public Set<String> getBadges() {
        return badges;
    }

    public void setBadges(Set<String> badges) {
        this.badges = badges;
    }
}