package com.iset.spring_integration.services;

import com.iset.spring_integration.entities.Cours;
import com.iset.spring_integration.repositories.CoursRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursService {
    @Autowired
    private CoursRepository coursRepository;

    public void deleteCours(Long id) {
        coursRepository.deleteById(id);
    }
}
