package com.iset.spring_integration.controllers;

import com.iset.spring_integration.entities.Warning;
import com.iset.spring_integration.services.WarningService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/warnings")
public class WarningController {
    @Autowired
    private WarningService warningService;

    @PostMapping("/{userId}")
    public ResponseEntity<Void> sendWarning(@PathVariable Long userId, @RequestBody String message) {
        warningService.sendWarning(userId, message);
        return ResponseEntity.ok().build();
    }
}
