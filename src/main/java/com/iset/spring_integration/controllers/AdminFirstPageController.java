package com.iset.spring_integration.controllers;

import com.iset.spring_integration.services.AdminFirstPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AdminFirstPageController {
    @Autowired
    private AdminFirstPageService adminFirstPageService;

    @GetMapping("/admin_stats")
    public ResponseEntity<Map<String, Long>> adminStats() {
        return new ResponseEntity<>(adminFirstPageService.AdminStats(),HttpStatus.OK);
    }
}
