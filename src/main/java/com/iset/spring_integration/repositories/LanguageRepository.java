package com.iset.spring_integration.repositories;

import com.iset.spring_integration.entities.LanguageCours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<LanguageCours, Long> {
    // Ajouter la méthode findByNom pour rechercher une langue par son nom
    Optional<LanguageCours> findByNom(String nom);
}

