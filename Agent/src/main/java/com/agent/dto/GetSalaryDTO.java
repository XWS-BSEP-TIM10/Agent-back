package com.agent.dto;

import com.agent.model.Salary;

public class GetSalaryDTO {
    private String id;
    private double value;
    private String position;

    public GetSalaryDTO(Salary salary) {
        this.id = salary.getId();
        this.position = salary.getPosition();
        this.value = salary.getValue();
    }

    public String getId() {
        return id;
    }

    public String getPosition() {
        return position;
    }

    public double getValue() {
        return value;
    }
}
