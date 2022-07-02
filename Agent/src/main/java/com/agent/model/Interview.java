package com.agent.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Interview {

    @Id
    private String id;

    private String title;

    private String hrInterview;

    private String techInterview;

    private String position;

    private Date creationDate;

    @ManyToOne
    private User candidate;

    @ManyToOne
    private Company company;

    public Interview(String id, String title, String hrInterview, String techInterview, String position, User candidate, Company company) {
        this.id = id;
        this.title = title;
        this.hrInterview = hrInterview;
        this.techInterview = techInterview;
        this.position = position;
        this.creationDate = new Date();
        this.candidate = candidate;
        this.company = company;
    }

    public Interview() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getHrInterview() {
        return hrInterview;
    }

    public String getTechInterview() {
        return techInterview;
    }

    public String getPosition() {
        return position;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public User getCandidate() {
        return candidate;
    }

    public Company getCompany() {
        return company;
    }
}
