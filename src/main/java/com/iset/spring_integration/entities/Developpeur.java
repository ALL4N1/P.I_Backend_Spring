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

    @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PendingRecruit> recruitApplications = new ArrayList<>();


    public List<PendingRecruit> getRecruitApplications() {
        return recruitApplications;
    }

    public void setRecruitApplications(List<PendingRecruit> recruitApplications) {
        this.recruitApplications = recruitApplications;
    }
}
