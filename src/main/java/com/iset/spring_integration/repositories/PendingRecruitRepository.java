package com.iset.spring_integration.repositories;

import com.iset.spring_integration.entities.PendingRecruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PendingRecruitRepository extends JpaRepository<PendingRecruit, Long> {
    @Query("SELECT pr FROM PendingRecruit pr JOIN FETCH pr.developer d JOIN FETCH d.recruitApplications ")
    List<PendingRecruit> findAllWithDeveloperAndTest();
}
