package com.iset.spring_integration.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RecruitRequestDTO {
    private Long testId;
    private Double score;

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public MultipartFile getCv() {
        return cv;
    }

    public void setCv(MultipartFile cv) {
        this.cv = cv;
    }

    private MultipartFile cv;
}
