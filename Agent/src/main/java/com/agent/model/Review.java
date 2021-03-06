package com.agent.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Review {

    @Id
    private String id;

    private String title;

    private String positive;

    private String negative;

    private String position;

    private double rating;

    @ManyToOne
    private User reviewer;

    @ManyToOne
    private Company company;

    private Date creationDate;

    public Review(String id, String title, String positive, String negative, String position, double rating) {
        this.id = id;
        this.title = title;
        this.positive = positive;
        this.negative = negative;
        this.position = position;
        this.rating = rating;
        this.creationDate = new Date();
    }

    public Review() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPositive() {
        return positive;
    }

    public String getNegative() {
        return negative;
    }

    public String getPosition() {
        return position;
    }

    public double getRating() {
        return rating;
    }

    public User getReviewer() {
        return reviewer;
    }

    public Company getCompany() {
        return company;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
