package com.iset.spring_integration.repositories;

import com.iset.spring_integration.entities.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    List<Enseignant> findAll(); // ou méthodes spécifiques
}
