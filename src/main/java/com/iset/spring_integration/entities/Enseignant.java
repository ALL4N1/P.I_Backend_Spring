package com.iset.spring_integration.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("ENSEIGNANT")
public class Enseignant extends Utilisateur {
    @OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL)
    private List<Cours> coursCrees;

    public List<Cours> getCoursCrees() {
        return coursCrees;
    }

    public void setCoursCrees(List<Cours> coursCrees) {
        this.coursCrees = coursCrees;
    }
}
