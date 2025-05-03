package com.iset.spring_integration.repositories;

import com.iset.spring_integration.entities.Developpeur;
import com.iset.spring_integration.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeveloppeurRepository extends JpaRepository<Developpeur, Long> {
    @Query("SELECT u FROM Utilisateur u WHERE TYPE(u) = Developpeur or TYPE(u) = Enseignant")
    List<Developpeur> findAllDevelopersAndTeachers();

    @Query("SELECT count(u) FROM Utilisateur u WHERE TYPE(u) = Developpeur or TYPE(u) = Enseignant")
    long countAllDevelopersAndTeachers();

    @Query("SELECT count(u) FROM Utilisateur u WHERE TYPE(u) = Enseignant")
    long countAllTeachers();

    @Query("SELECT count(dev) FROM Developpeur dev WHERE dev.isBanned = true")
    long countBannedDevelopers();

    @Query("SELECT count(ens) FROM Enseignant ens WHERE ens.isBanned = true")
    long countBannedEnseignants();
}
