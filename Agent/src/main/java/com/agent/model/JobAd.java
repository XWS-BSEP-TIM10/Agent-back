package com.agent.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class JobAd {

    @Id
    private String id;

    private String title;

    private String position;

    private String description;

    @ManyToOne
    private Company company;

    public JobAd(String id, String title, String position, String description, Company company) {
        this.id = id;
        this.title = title;
        this.position = position;
        this.description = description;
        this.company = company;
    }

    public JobAd() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosition() {
        return position;
    }

    public String getDescription() {
        return description;
    }

    public Company getCompany() {
        return company;
    }
}
