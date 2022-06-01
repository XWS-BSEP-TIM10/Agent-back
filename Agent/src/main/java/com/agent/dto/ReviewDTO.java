package com.agent.dto;

import com.agent.model.Review;

import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;

public class ReviewDTO {

    private String title;

    private String positive;

    private String negative;

    private String position;

    private double rating;

    private String creationDate;

    public ReviewDTO() {
    }

    public ReviewDTO(String title, String positive, String negative, String position, double rating) {
        this.title = title;
        this.positive = positive;
        this.negative = negative;
        this.position = position;
        this.rating = rating;
    }

    public ReviewDTO(Review review) {
        this.title = review.getTitle();
        this.positive = review.getPositive();
        this.negative = review.getNegative();
        this.position = review.getPosition();
        this.rating = review.getRating();
        this.creationDate = new SimpleDateFormat("dd/MM/yyyy").format(review.getCreationDate());
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
}
