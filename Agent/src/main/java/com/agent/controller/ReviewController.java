package com.agent.controller;

import com.agent.dto.NewReviewRequestDTO;
import com.agent.dto.NewReviewResponseDTO;
import com.agent.dto.ReviewDTO;
import com.agent.exception.CompanyNotFoundException;
import com.agent.exception.UserNotFoundException;
import com.agent.model.Review;
import com.agent.service.LoggerService;
import com.agent.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ReviewController {

    private final ReviewService reviewService;

    private final LoggerService loggerService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
        this.loggerService = new LoggerService(this.getClass());
    }

    @PreAuthorize("hasAuthority('CREATE_REVIEW_PERMISSION')")
    @PostMapping("reviews")
    public ResponseEntity<NewReviewResponseDTO> addReview(@Valid @RequestBody NewReviewRequestDTO newReviewDTO) {
        try {
            Review review = reviewService.addReview(newReviewDTO);
            if(review == null) {
                loggerService.reviewCreatingFailed("Review saving failed", SecurityContextHolder.getContext().getAuthentication().getName());
                return ResponseEntity.internalServerError().build();
            }
            loggerService.reviewCreated(SecurityContextHolder.getContext().getAuthentication().getName());
            return ResponseEntity.ok(new NewReviewResponseDTO(review));
        } catch (UserNotFoundException |CompanyNotFoundException exception) {
            loggerService.reviewCreatingFailed(exception.getMessage(), SecurityContextHolder.getContext().getAuthentication().getName());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("companies/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> getCompanyReview(@PathVariable String id) {
        try {
            List<Review> reviews = reviewService.getCompanyReviews(id);
            List<ReviewDTO> dtos = new ArrayList<>();
            for (Review review : reviews)
                dtos.add(new ReviewDTO(review));
            return ResponseEntity.ok(dtos);
        } catch (CompanyNotFoundException exception){
            loggerService.reviewsGettingFailed(exception.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
