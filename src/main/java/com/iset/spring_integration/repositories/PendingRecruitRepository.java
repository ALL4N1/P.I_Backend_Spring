package com.iset.spring_integration.repositories;

import com.iset.spring_integration.entities.PendingRecruit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingRecruitRepository extends JpaRepository<PendingRecruit, Long> {
}
