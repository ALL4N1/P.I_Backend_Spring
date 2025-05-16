package com.iset.spring_integration.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class QuestionResponses {
    @Column(name = "reponse_a", columnDefinition = "TEXT")
    private String A;

    @Column(name = "reponse_b", columnDefinition = "TEXT")
    private String B;

    @Column(name = "reponse_c", columnDefinition = "TEXT")
    private String C;

    @Column(name = "reponse_d", columnDefinition = "TEXT")
    private String D;

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }
}