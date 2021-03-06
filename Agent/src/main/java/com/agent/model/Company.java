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

    public Company() {
    }

    public Company(String id, String name, String address, String website, String phoneNumber, String email, String description) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.website = website;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.description = description;
        this.rating = 0.0;
        this.activated = false;
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


    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public double getRating() {
        return rating;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
