package com.iset.spring_integration.entities;

import com.iset.spring_integration.dto.AdminPendingRecruitDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "AdminPendingRecruitMapping",
                classes = @ConstructorResult(
                        targetClass = AdminPendingRecruitDTO.class,
                        columns = {
                                @ColumnResult(name = "id", type = Long.class),
                                @ColumnResult(name = "developerName", type = String.class),
                                @ColumnResult(name = "developerImage", type = String.class),
                                @ColumnResult(name = "developerEmail", type = String.class),
                                @ColumnResult(name = "testTitle", type = String.class),
                                @ColumnResult(name = "testLanguage", type = String.class),
                                @ColumnResult(name = "testScore", type = Double.class),
                                @ColumnResult(name = "status", type = String.class),
                                @ColumnResult(name = "formattedSubmitDate", type = String.class),
                                @ColumnResult(name = "cvUrl", type = String.class),
                                @ColumnResult(name = "developerId", type = Long.class)
                        }
                )
        )
})
@NamedNativeQuery(
        name = "PendingRecruit.findAdminRecruits",
        query = """
        SELECT 
            pr.id AS id,
            d.nom AS developerName,
            d.pfp_url AS developerImage,
            d.email AS developerEmail,
            t.title AS testTitle,
            t.language AS testLanguage,
            pr.test_score AS testScore,
            pr.status AS status,
            TO_CHAR(pr.submit_date, 'DD/MM/YYYY HH24:MI') AS formattedSubmitDate, 
            pr.cv_url AS cvUrl,
            d.id AS developerId
        FROM pending_recruit pr
        JOIN utilisateur d ON pr.developer_id = d.id
        JOIN test t ON pr.test_id = t.id
    """,
        resultSetMapping = "AdminPendingRecruitMapping"
)
public class PendingRecruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id", nullable = false)
    private Developpeur developer;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date submitDate;

    @Column(name = "test_language", nullable = false) // Nom de colonne explicite
    private String testLanguage;

    @Column(nullable = false)
    private Double testScore;

    @Column(name = "cv_url", nullable = false)
    private String cvUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitStatus status;


    // Constructeur personnalisé si nécessaire
    public PendingRecruit(Test test, Developpeur developer, Date submitDate,
                          String testLanguage, Double testScore, String cvUrl,
                          RecruitStatus status) {
        this.test = test;
        this.developer = developer;
        this.submitDate = submitDate;
        this.testLanguage = testLanguage;
        this.testScore = testScore;
        this.cvUrl = cvUrl;
        this.status = status;
    }
    public PendingRecruit(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Developpeur getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developpeur developer) {
        this.developer = developer;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public String getTestLanguage() {
        return testLanguage;
    }

    public void setTestLanguage(String testLanguage) {
        this.testLanguage = testLanguage;
    }

    public Double getTestScore() {
        return testScore;
    }

    public void setTestScore(Double testScore) {
        this.testScore = testScore;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public RecruitStatus getStatus() {
        return status;
    }

    public void setStatus(RecruitStatus status) {
        this.status = status;
    }
}