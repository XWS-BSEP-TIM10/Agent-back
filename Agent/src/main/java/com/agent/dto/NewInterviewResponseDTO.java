package com.agent.dto;

import com.agent.model.Interview;

import java.text.SimpleDateFormat;

public class NewInterviewResponseDTO {

    private String id;

    private String title;

    private String hrInterview;

    private String techInterview;

    private String position;

    private String creationDate;

    private String candidateId;

    private String companyId;

    public NewInterviewResponseDTO() {
    }

    public NewInterviewResponseDTO(String id, String title, String hrInterview, String techInterview, String position, String creationDate, String candidateId, String companyId) {
        this.id = id;
        this.title = title;
        this.hrInterview = hrInterview;
        this.techInterview = techInterview;
        this.position = position;
        this.creationDate = creationDate;
        this.candidateId = candidateId;
        this.companyId = companyId;
    }

    public NewInterviewResponseDTO(Interview interview) {
        this.id = interview.getId();
        this.title = interview.getTitle();
        this.hrInterview = interview.getHrInterview();
        this.techInterview = interview.getTechInterview();
        this.position = interview.getPosition();
        this.creationDate = new SimpleDateFormat("dd/MM/yyyy").format(interview.getCreationDate());
        this.candidateId = interview.getCandidate().getId();
        this.companyId = interview.getCompany().getId();
    }

    public String getId() {
        return id;
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

    public String getCreationDate() {
        return creationDate;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public String getCompanyId() {
        return companyId;
    }
}
