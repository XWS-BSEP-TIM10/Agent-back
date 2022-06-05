package com.agent.dto;

import com.agent.model.JobAd;
import com.agent.model.Requirement;

import java.util.List;

public class GetJobAdDTO {
    private final String id;

    private final String title;

    private final String position;

    private final String description;

    private final String companyId;

    private final List<String> requirements;

    public GetJobAdDTO(JobAd jobAd) {
        this.id = jobAd.getId();
        this.title = jobAd.getTitle();
        this.position = jobAd.getPosition();
        this.description = jobAd.getDescription();
        this.companyId = jobAd.getCompany().getId();
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

    public String getCompanyId() {
        return companyId;
    }

    public List<String> getRequirements() {
        return requirements;
    }

    public String getId() {
        return id;
    }
}
