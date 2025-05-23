package com.iset.spring_integration.repositories;

import com.iset.spring_integration.dto.AdminPendingRecruitDTO;
import com.iset.spring_integration.entities.Developpeur;
import com.iset.spring_integration.entities.PendingRecruit;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PendingRecruitRepository extends JpaRepository<PendingRecruit, Long> {
    // Dans votre repository
    @Query(nativeQuery = true, name = "PendingRecruit.findAdminRecruits")
    List<AdminPendingRecruitDTO> findAllWithDeveloperAndTest();

    List<PendingRecruit> findAllByDeveloper(Developpeur developer);
}
