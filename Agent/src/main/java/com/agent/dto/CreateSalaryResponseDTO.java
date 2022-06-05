package com.agent.dto;

import com.agent.model.Salary;

public class CreateSalaryResponseDTO {

    private double value;

    private String position;

    private String employeeId;

    private String companyId;

    public CreateSalaryResponseDTO(Salary salary) {
        this.value = salary.getValue();
        this.position = salary.getPosition();
        this.employeeId = salary.getEmployee().getId();
        this.companyId = salary.getCompany().getId();
    }

    public CreateSalaryResponseDTO() {
    }

    public double getValue() {
        return value;
    }

    public String getPosition() {
        return position;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getCompanyId() {
        return companyId;
    }
}
