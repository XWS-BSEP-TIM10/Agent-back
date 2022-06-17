package com.agent.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CreateCompanyRequestDTO {
    public CreateCompanyRequestDTO() {
        /* DTO fields filled by reflection, empty constructor necessary */
    }

    @NotBlank
    private String ownerId;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private String website;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String description;

    public String getOwnerId() {
        return ownerId;
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
}
