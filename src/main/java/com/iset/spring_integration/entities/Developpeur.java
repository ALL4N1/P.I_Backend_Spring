package com.iset.spring_integration.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
@DiscriminatorValue("DEVELOPPEUR")
public class Developpeur extends Utilisateur {
    // Ajouter des attributs spécifiques si nécessaire
    @OneToMany(mappedBy = "developpeur", cascade = CascadeType.ALL)
    private List<Report> reports;
}
