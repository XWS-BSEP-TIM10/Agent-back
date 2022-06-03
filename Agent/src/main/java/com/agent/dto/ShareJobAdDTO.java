package com.agent.dto;

import com.agent.model.JobAd;
import com.agent.model.Requirement;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ShareJobAdDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String position;

    @NotBlank
    private String description;

    @NotBlank
    private String company;

    @NotNull
    private List<String> requirements;

    public ShareJobAdDTO() {
    }

    public ShareJobAdDTO(String title, String position, String description, String company, List<String> requirements) {
        this.title = title;
        this.position = position;
        this.description = description;
        this.company = company;
        this.requirements = requirements;
    }

    public ShareJobAdDTO(JobAd jobAd) {
        this.title = jobAd.getTitle();
        this.position = jobAd.getPosition();
        this.description = jobAd.getDescription();
        this.company = jobAd.getCompany().getName();
        this.requirements = jobAd.getRequirements().stream().map(Requirement::getName).toList();
    }

    public String getTitle() {
        return title;
    }

    public String getPosition() {
        return position;
    }

    public String getDescription() {
        return description;
    }

    public String getCompany() {
        return company;
    }

    public List<String> getRequirements() {
        return requirements;
    }
}
