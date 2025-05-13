package com.iset.spring_integration.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("DEVELOPPEUR")
public class Developpeur extends Utilisateur {
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isBanned;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer warnings;
    @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PendingRecruit> recruitApplications = new ArrayList<>();


    public Boolean getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(Boolean banned) {
        isBanned = banned;
    }

    public Integer getWarnings() {
        return warnings;
    }
    public void setWarnings(Integer warnings) {
        this.warnings = warnings;
    }

    public Boolean getBanned() {
        return isBanned;
    }

    public void setBanned(Boolean banned) {
        isBanned = banned;
    }

    public List<PendingRecruit> getRecruitApplications() {
        return recruitApplications;
    }

    public void setRecruitApplications(List<PendingRecruit> recruitApplications) {
        this.recruitApplications = recruitApplications;
    }
}
