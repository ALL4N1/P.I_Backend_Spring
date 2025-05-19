package com.iset.spring_integration.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UtilisateurChatDTO {
    private Long id;
    private String nom;
    private String email;
    private String pfp_url;
    private Set<String> badges;
    public UtilisateurChatDTO() {}

    public UtilisateurChatDTO(Long id, String nom, String email, String pfp_url, Set<String> badges) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.pfp_url = pfp_url;
        this.badges = badges;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPfp_url() {
        return pfp_url;
    }

    public void setPfp_url(String pfp_url) {
        this.pfp_url = pfp_url;
    }

    public Set<String> getBadges() {
        return badges;
    }

    public void setBadges(Set<String> badges) {
        this.badges = badges;
    }
    // null si Developpeur
}
