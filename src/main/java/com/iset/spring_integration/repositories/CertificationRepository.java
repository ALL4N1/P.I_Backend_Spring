package com.iset.spring_integration.repositories;

import com.iset.spring_integration.entities.Certification;
import com.iset.spring_integration.entities.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {
    List<Certification> findByEnseignant(Enseignant enseignant);

}