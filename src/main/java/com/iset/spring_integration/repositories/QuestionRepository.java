package com.iset.spring_integration.repositories;

import com.iset.spring_integration.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
