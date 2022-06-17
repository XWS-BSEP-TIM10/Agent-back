package com.agent.dto;

import com.agent.model.Review;

import java.text.SimpleDateFormat;

public class NewReviewResponseDTO {

    private String id;

    private String title;

    private String positive;

    private String negative;

    private String position;

    private double rating;

    private String reviewerId;

    private String companyId;

    private String creationDate;

    public NewReviewResponseDTO() {
    }

    public NewReviewResponseDTO(Review review) {
        this.id = review.getId();
        this.title = review.getTitle();
        this.positive = review.getPositive();
        this.negative = review.getNegative();
        this.position = review.getPosition();
        this.rating = review.getRating();
        this.reviewerId = review.getReviewer().getId();
        this.companyId = review.getCompany().getId();
        this.creationDate = new SimpleDateFormat("dd/MM/yyyy").format(review.getCreationDate());
    }

    public String getId() {
        return id;
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

    public String getCreationDate() {
        return creationDate;
    }
}
