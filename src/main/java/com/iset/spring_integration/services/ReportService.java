package com.iset.spring_integration.services;

import com.iset.spring_integration.entities.Report;
import com.iset.spring_integration.repositories.ReportRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }
}
