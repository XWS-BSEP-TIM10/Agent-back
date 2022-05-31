package com.agent.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Salary {

    @Id
    private String id;

    private double value;

    private String position;

    @ManyToOne
    private User employee;

    @ManyToOne
    private Company company;

    public Salary(String id, double value, String position, User employee, Company company) {
        this.id = id;
        this.value = value;
        this.position = position;
        this.employee = employee;
        this.company = company;
    }

    public Salary() {
    }

    public String getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public String getPosition() {
        return position;
    }

    public User getEmployee() {
        return employee;
    }

    public Company getCompany() {
        return company;
    }
}
