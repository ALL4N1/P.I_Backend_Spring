package com.iset.spring_integration.repositories;

import com.iset.spring_integration.entities.Developpeur;
import com.iset.spring_integration.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeveloppeurRepository extends JpaRepository<Developpeur, Long> {
    @Query("SELECT u FROM Utilisateur u WHERE TYPE(u) = Developpeur or TYPE(u) = Enseignant")
    List<Developpeur> findAllDevelopersAndTeachers();
    

}
