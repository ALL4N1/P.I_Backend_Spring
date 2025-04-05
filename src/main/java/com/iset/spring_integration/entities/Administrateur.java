package com.iset.spring_integration.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("ADMIN")
public class Administrateur extends Utilisateur {
    // Aucun champ  pour le moment

}
