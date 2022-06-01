package com.agent.dto;

import com.agent.model.Interview;

import java.text.SimpleDateFormat;

public class InterviewDTO {

    private String title;

    private String hrInterview;

    private String techInterview;

    private String position;

    private String creationDate;

    public InterviewDTO() {
    }

    public InterviewDTO(String title, String hrInterview, String techInterview, String position, String creationDate) {
        this.title = title;
        this.hrInterview = hrInterview;
        this.techInterview = techInterview;
        this.position = position;
        this.creationDate = creationDate;
    }

    public InterviewDTO(Interview interview) {
        this.title = interview.getTitle();
        this.hrInterview = interview.getHrInterview();
        this.techInterview = interview.getTechInterview();
        this.position = interview.getPosition();
        this.creationDate = new SimpleDateFormat("dd/MM/yyyy").format(interview.getCreationDate());
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
}
