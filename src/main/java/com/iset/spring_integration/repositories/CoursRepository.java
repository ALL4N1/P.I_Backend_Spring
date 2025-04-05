package com.iset.spring_integration.repositories;

import com.iset.spring_integration.entities.Cours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursRepository extends JpaRepository<Cours, Long> {
}

