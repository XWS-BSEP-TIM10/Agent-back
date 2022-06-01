package com.agent.dto;

import com.agent.model.Salary;

public class GetSalaryDTO {
    private String id;
    private double value;

    public GetSalaryDTO(Salary salary) {
        this.id = salary.getId();
        this.value = salary.getValue();
    }

    public String getId() {
        return id;
    }

    public double getValue() {
        return value;
    }
}
