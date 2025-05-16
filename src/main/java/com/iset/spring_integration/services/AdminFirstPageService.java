package com.iset.spring_integration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iset.spring_integration.repositories.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminFirstPageService {
    @Autowired
    private DeveloppeurRepository devRepository;

    @Autowired
    private RecruitsRepository recruitsRepo;



    public Map<String,Long> AdminStats(){
        Map<String,Long> map = new HashMap<>();
        map.put("Total Developers", devRepository.countAllDevelopersAndTeachers());
        map.put("Approved Teachers", devRepository.countAllTeachers());
        map.put("Pending Teacher Apps", recruitsRepo.count());

        return map;
    }
}
