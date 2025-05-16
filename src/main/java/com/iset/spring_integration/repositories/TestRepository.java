package com.iset.spring_integration.repositories;

import com.iset.spring_integration.entities.Test;
import org.springframework.data.jpa.repository.*;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {

    // Charge les tests avec les questions en une seule requête
    @EntityGraph(attributePaths = {"questions", "difficultyLevels"})
    @Query("SELECT t FROM Test t")
    List<Test> findAllWithQuestions();
}
