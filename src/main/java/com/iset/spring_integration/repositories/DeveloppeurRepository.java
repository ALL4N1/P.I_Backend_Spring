package com.iset.spring_integration.repositories;

import com.iset.spring_integration.entities.Developpeur;
import com.iset.spring_integration.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DeveloppeurRepository extends JpaRepository<Developpeur, Long> {
    @Query("SELECT u FROM Utilisateur u WHERE TYPE(u) = Developpeur or TYPE(u) = Enseignant")
    List<Developpeur> findAllDevelopersAndTeachers();

    @Query("SELECT count(u) FROM Utilisateur u WHERE TYPE(u) = Developpeur or TYPE(u) = Enseignant")
    long countAllDevelopersAndTeachers();

    @Query("SELECT count(u) FROM Utilisateur u WHERE TYPE(u) = Enseignant")
    long countAllTeachers();

    Optional<Developpeur> findByEmail(String email);

    List<Developpeur> findAll();

}
