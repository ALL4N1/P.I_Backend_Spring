package com.iset.spring_integration.repositories;
import com.iset.spring_integration.entities.Report;
import com.iset.spring_integration.entities.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByStatus(ReportStatus status);

    @Query(value = "SELECT count(*) FROM report where status = 'PENDING'", nativeQuery = true)
    long countPending();
}

