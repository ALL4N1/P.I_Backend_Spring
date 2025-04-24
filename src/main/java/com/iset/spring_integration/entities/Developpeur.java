package com.iset.spring_integration.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("DEVELOPPEUR")
public class Developpeur extends Utilisateur {
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isBanned;
}
