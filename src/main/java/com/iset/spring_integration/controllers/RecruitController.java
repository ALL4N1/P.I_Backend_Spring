package com.iset.spring_integration.controllers;

import com.iset.spring_integration.entities.PendingRecruit;
import com.iset.spring_integration.dto.RecruitRequestDTO;
import com.iset.spring_integration.services.RecruitService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recruitments")

public class RecruitController {

    private final RecruitService recruitService;

    public RecruitController(RecruitService recruitService) {
        this.recruitService = recruitService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> submitRecruitment(
            @ModelAttribute RecruitRequestDTO request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        try {
            PendingRecruit result = recruitService.createRecruitment(request, userDetails.getUsername());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
