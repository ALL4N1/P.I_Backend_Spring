package com.iset.spring_integration.entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("VIDEO")
public class ChapitreVideo extends Chapitre {
    private String urlVideo;

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }
    // Getters & Setters
}

