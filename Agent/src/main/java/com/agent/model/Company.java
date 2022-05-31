package com.agent.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Company {

    @Id
    private String id;

    private String name;

    private String address;

    private String website;

    private String phoneNumber;

    private String email;

    private String description;

    @OneToOne
    private User owner;

    private double rating;

    private boolean activated;

    public Company(String id, String name, String address, String website, String phoneNumber, String email, String description, User owner, boolean activated) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.website = website;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.description = description;
        this.owner = owner;
        this.rating = 0.0;
        this.activated = activated;
    }

    public Company() {

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getWebsite() {
        return website;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public User getOwner() {
        return owner;
    }

    public double getRating() {
        return rating;
    }

    public boolean isActivated() {
        return activated;
    }
}
