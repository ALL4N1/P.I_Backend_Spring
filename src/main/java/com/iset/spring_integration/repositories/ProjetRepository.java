package com.iset.spring_integration.repositories;

import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
    List<Projet> findByEnseignant(Enseignant enseignant);
}