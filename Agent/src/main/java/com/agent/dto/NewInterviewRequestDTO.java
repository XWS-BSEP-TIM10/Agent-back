package com.agent.dto;

import javax.validation.constraints.NotBlank;

public class NewInterviewRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String hrInterview;

    @NotBlank
    private String techInterview;

    @NotBlank
    private String position;

    @NotBlank
    private String candidateId;

    @NotBlank
    private String companyId;

    public NewInterviewRequestDTO() {
    }

    public NewInterviewRequestDTO(String title, String hrInterview, String techInterview, String position, String candidateId, String companyId) {
        this.title = title;
        this.hrInterview = hrInterview;
        this.techInterview = techInterview;
        this.position = position;
        this.candidateId = candidateId;
        this.companyId = companyId;
    }

    public String getTitle() {
        return title;
    }

    public String getHrInterview() {
        return hrInterview;
    }

    public String getTechInterview() {
        return techInterview;
    }

    public String getPosition() {
        return position;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public String getCompanyId() {
        return companyId;
    }
}
