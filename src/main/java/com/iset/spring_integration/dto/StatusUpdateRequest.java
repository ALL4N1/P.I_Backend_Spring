package com.iset.spring_integration.dto;

import com.iset.spring_integration.entities.RecruitStatus;

// StatusUpdateRequest.java
public class StatusUpdateRequest {
    private RecruitStatus status;
    private String comments;

    public RecruitStatus getStatus() {
        return status;
    }

    public void setStatus(RecruitStatus status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
