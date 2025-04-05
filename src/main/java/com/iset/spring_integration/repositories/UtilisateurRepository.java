package com.iset.spring_integration.repositories;

import com.iset.spring_integration.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
}

