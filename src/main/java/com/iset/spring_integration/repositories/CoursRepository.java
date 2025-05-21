package com.iset.spring_integration.repositories;

import com.iset.spring_integration.entities.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoursRepository extends JpaRepository<Cours, Long> {
    @Query(value = "select distinct subject from cours", nativeQuery = true)
    List<String> getSubjects();
}

