package com.iset.spring_integration.repositories;

import com.iset.spring_integration.entities.PendingRecruits;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitsRepository extends JpaRepository<PendingRecruits, Long> {
}
