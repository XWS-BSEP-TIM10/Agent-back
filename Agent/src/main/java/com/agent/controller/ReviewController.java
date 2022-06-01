package com.agent.controller;

import com.agent.dto.CreateCompanyRequestDTO;
import com.agent.dto.CreateCompanyResponseDTO;
import com.agent.dto.NewReviewRequestDTO;
import com.agent.exception.UserNotFoundException;
import com.agent.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<NewReviewRequestDTO> addReview(@Valid @RequestBody NewReviewRequestDTO newReviewDTO) {
        try {
            //NewReviewRequestDTO responseDTO = reviewService.add(newReviewDTO);
            //return ResponseEntity.ok(responseDTO);
            return ResponseEntity.badRequest().build();
        } catch (UserNotFoundException userNotFoundException) {
            return ResponseEntity.notFound().build();
        }
    }
}
