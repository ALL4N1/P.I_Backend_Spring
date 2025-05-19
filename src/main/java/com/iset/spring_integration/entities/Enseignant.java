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
    @OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL)
    private List<Cours> coursList;

    @Column(name = "badges", columnDefinition = "TEXT")
    @Convert(converter = StringSetConverter.class)
    private Set<String> badges = new HashSet<>();



    public List<Cours> getCoursList() {
        return coursList;
    }

    public void setCoursList(List<Cours> coursCrees) {
        this.coursList = coursCrees;
    }

    public Set<String> getBadges() {
        return badges;
    }

    public void setBadges(Set<String> badges) {
        this.badges = badges;
    }
}
