package com.agent.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class CreateSalaryRequestDTO {

    @Min(value = 0)
    private double value;

    @NotBlank
    private String position;

    @NotBlank
    private String employeeId;

    @NotBlank
    private String companyId;

    public CreateSalaryRequestDTO() {
        /* DTO fields filled by reflection, empty constructor necessary */
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
