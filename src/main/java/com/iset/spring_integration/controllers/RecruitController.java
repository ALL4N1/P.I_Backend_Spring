/*package com.iset.spring_integration.controllers;

import com.iset.spring_integration.dto.NewRecruit;
import com.iset.spring_integration.entities.PendingRecruit;
import com.iset.spring_integration.services.RecruitService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruits")
public class RecruitController {
    @Autowired
    private RecruitService recruitService;

    @GetMapping("/list")
    public ResponseEntity<List<PendingRecruit>> getRecruitList(){
        return new ResponseEntity<>(recruitService.getPendingRecruits(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<PendingRecruit> addRecruit(@RequestBody NewRecruit recruit){
        try {
            PendingRecruit newRecruit = recruitService.addRecruit(
                    recruit.getIdDev(),recruit.getTestScore(),recruit.getTestLanguage(),recruit.getCvUrl());
            return new ResponseEntity<>(newRecruit, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}/deny")
    public ResponseEntity<String> denyRecruit(@PathVariable Long id){
        try{
            recruitService.rejectApp(id);
            return ResponseEntity.ok("App number " + id + " denied");
        }
        catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch(Exception e){
            return ResponseEntity.internalServerError().body("Error denying application: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}/approve")
    public ResponseEntity<String> approveRecruit(@PathVariable Long id){
        try{
            recruitService.approveApp(id);
            return ResponseEntity.ok("App number " + id + " approved");
        }
        catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch(Exception e){
            return ResponseEntity.internalServerError().body("Error approving application: " + e.getMessage());
        }
    }
}
*/