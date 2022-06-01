package com.agent.service;

import com.agent.dto.NewReviewRequestDTO;
import com.agent.exception.CompanyNotFoundException;
import com.agent.exception.UserNotFoundException;
import com.agent.model.Company;
import com.agent.model.Review;
import com.agent.model.User;
import com.agent.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    private final ReviewRepository repository;

    private final UserService userService;

    private final CompanyService companyService;

    public ReviewService(ReviewRepository repository, UserService userService, CompanyService companyService) {
        this.repository = repository;
        this.userService = userService;
        this.companyService = companyService;
    }

    public Review addReview(NewReviewRequestDTO newReviewDTO) {
        User reviewer = userService.findById(newReviewDTO.getReviewerId()).orElseThrow(UserNotFoundException::new);
        Company company = companyService.findById(newReviewDTO.getCompanyId()).orElseThrow(CompanyNotFoundException::new);
        Review newReview = new Review(UUID.randomUUID().toString(),
                newReviewDTO.getTitle(),
                newReviewDTO.getPositive(),
                newReviewDTO.getNegative(),
                newReviewDTO.getPosition(),
                newReviewDTO.getRating(),
                reviewer, company, new Date());
        return repository.save(newReview);
    }

    public List<Review> getCompanyReviews(String id) {
        Company company = companyService.findById(id).orElseThrow(CompanyNotFoundException::new);
        return repository.findByCompany(company);
    }
}
