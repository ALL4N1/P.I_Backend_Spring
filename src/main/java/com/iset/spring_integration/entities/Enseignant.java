package com.iset.spring_integration.entities;

import com.iset.spring_integration.util.StringSetConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("ENSEIGNANT")
public class Enseignant extends Developpeur {
    @OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL)
    private List<Cours> coursCrees;

    @Column(name = "badges")
    @Convert(converter = StringSetConverter.class)
    private Set<String> badges = new HashSet<>();

    public List<Cours> getCoursCrees() {
        return coursCrees;
    }

    public void setCoursCrees(List<Cours> coursCrees) {
        this.coursCrees = coursCrees;
    }

    public Set<String> getBadges() {
        return badges;
    }

    public void setBadges(Set<String> badges) {
        this.badges = badges;
    }
}
