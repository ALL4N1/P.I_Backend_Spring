package com.iset.spring_integration.repositories;

import com.iset.spring_integration.entities.TestRecrutement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRecrutementRepository extends JpaRepository<TestRecrutement, Long> {
}

