package com.iset.spring_integration.entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("PDF")
public class ChapitrePdf extends Chapitre {
    private String urlPdf;

    public String getUrlPdf() {
        return urlPdf;
    }

    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }

    // Getters & Setters
}
