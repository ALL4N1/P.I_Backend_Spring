package com.iset.spring_integration.controllers;

import com.iset.spring_integration.services.FirstPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class FirstPageController {
    @Autowired
    private FirstPageService firstPageService;

    @GetMapping("/admin_stats")
    public ResponseEntity<Map<String, Long>> adminStats() {
        return new ResponseEntity<>(firstPageService.AdminStats(),HttpStatus.OK);
    }
}
