package com.iset.spring_integration.repositories;

import com.iset.spring_integration.entities.Chapitre;
import com.iset.spring_integration.entities.Cours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapitreRepository extends JpaRepository<Chapitre, Long> {
    List<Chapitre> findChapitreByCours(Cours cours);
}
