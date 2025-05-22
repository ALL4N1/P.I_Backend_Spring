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

    @Column(columnDefinition = "TEXT")
    private String bio;


    @Column(name = "badges", columnDefinition = "TEXT")
    @Convert(converter = StringSetConverter.class)
    private Set<String> badges = new HashSet<>();

    public Set<String> getBadges() {
        return badges;
    }

    public void setBadges(Set<String> badges) {
        this.badges = badges;
    }

// Getters and Setters


    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }


}