package com.agent.dto;

import com.agent.model.Company;

public class CreateCompanyResponseDTO {
    private String id;

    private String ownerId;


    private String name;


    private String address;


    private String website;


    private String phoneNumber;


    private String email;


    private String description;

    public CreateCompanyResponseDTO(Company company) {
        this.id = company.getId();
        this.ownerId = company.getOwner().getId();
        this.name = company.getName();
        this.address = company.getAddress();
        this.website = company.getWebsite();
        this.phoneNumber = company.getPhoneNumber();
        this.email = company.getEmail();
        this.description = company.getDescription();
    }


    public String getId() {
        return id;
    }

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
