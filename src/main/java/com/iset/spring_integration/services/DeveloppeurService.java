package com.iset.spring_integration.services;

import com.iset.spring_integration.entities.Cours;
import com.iset.spring_integration.entities.Developpeur;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.repositories.CoursRepository;
import com.iset.spring_integration.repositories.DeveloppeurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloppeurService {
    @Autowired
    private DeveloppeurRepository devRepository;

    @Autowired
    private CoursRepository coursRepository;

    public List<Developpeur> findAll() {
        return devRepository.findAllDevelopersAndTeachers();
    }





}
