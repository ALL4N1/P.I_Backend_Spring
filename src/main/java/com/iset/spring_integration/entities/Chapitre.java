package com.iset.spring_integration.entities;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class Chapitre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer placement;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private String urlChapitre;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeChapitre typeChapitre;

    @ManyToOne
    @JoinColumn(name = "cours_id", nullable = false)
    private Cours cours;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPlacement() {
        return placement;
    }

    public void setPlacement(Integer placement) {
        this.placement = placement;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getUrlChapitre() {
        return urlChapitre;
    }

    public void setUrlChapitre(String urlChapitre) {
        this.urlChapitre = urlChapitre;
    }

    public TypeChapitre getTypeChapitre() {
        return typeChapitre;
    }

    public void setTypeChapitre(TypeChapitre typeChapitre) {
        this.typeChapitre = typeChapitre;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }
}

