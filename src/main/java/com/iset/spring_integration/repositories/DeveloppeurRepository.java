package com.iset.spring_integration.repositories;

import com.iset.spring_integration.entities.Developpeur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloppeurRepository extends JpaRepository<Developpeur, Long> {}
