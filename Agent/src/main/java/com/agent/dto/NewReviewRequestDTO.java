package com.agent.dto;

import javax.validation.constraints.NotBlank;

public class NewReviewRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String positive;

    @NotBlank
    private String negative;

    @NotBlank
    private String position;

    @NotBlank
    private double rating;

    @NotBlank
    private String reviewerId;

    @NotBlank
    private String companyId;

    public NewReviewRequestDTO(String title, String positive, String negative, String position, double rating, String reviewerId, String companyId) {
        this.title = title;
        this.positive = positive;
        this.negative = negative;
        this.position = position;
        this.rating = rating;
        this.reviewerId = reviewerId;
        this.companyId = companyId;
    }

    public NewReviewRequestDTO() {
    }

    public String getTitle() {
        return title;
    }

    public String getPositive() {
        return positive;
    }

    public String getNegative() {
        return negative;
    }

    public String getPosition() {
        return position;
    }

    public double getRating() {
        return rating;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public String getCompanyId() {
        return companyId;
    }
}
