package com.agent.controller;

import com.agent.dto.NewReviewRequestDTO;
import com.agent.dto.NewReviewResponseDTO;
import com.agent.dto.ReviewDTO;
import com.agent.exception.CompanyNotFoundException;
import com.agent.exception.UserNotFoundException;
import com.agent.model.Review;
import com.agent.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PreAuthorize("hasAuthority('CREATE_REVIEW_PERMISSION')")
    @PostMapping("reviews")
    public ResponseEntity<NewReviewResponseDTO> addReview(@Valid @RequestBody NewReviewRequestDTO newReviewDTO) {
        try {
            Review review = reviewService.addReview(newReviewDTO);
            if(review == null)
                return ResponseEntity.internalServerError().build();
            return ResponseEntity.ok(new NewReviewResponseDTO(review));
        } catch (UserNotFoundException |CompanyNotFoundException exception) {
            exception.printStackTrace();
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
            exception.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
