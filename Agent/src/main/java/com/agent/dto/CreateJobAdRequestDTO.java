package com.agent.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CreateJobAdRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String position;

    @NotBlank
    private String description;

    @NotBlank
    private String companyId;

    @NotNull
    private List<String> requirements;

    public CreateJobAdRequestDTO() {
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

    public String getCompanyId() {
        return companyId;
    }

    public List<String> getRequirements() {
        return requirements;
    }
}
