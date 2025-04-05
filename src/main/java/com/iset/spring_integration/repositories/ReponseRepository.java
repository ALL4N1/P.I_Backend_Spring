package com.iset.spring_integration.repositories;

import com.iset.spring_integration.entities.Reponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReponseRepository extends JpaRepository<Reponse, Long> {
    List<Reponse> findByQuestionId(Long questionId);

}
