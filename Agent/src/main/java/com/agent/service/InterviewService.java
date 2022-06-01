package com.agent.service;

import com.agent.dto.NewInterviewRequestDTO;
import com.agent.exception.CompanyNotFoundException;
import com.agent.exception.UserNotFoundException;
import com.agent.model.Company;
import com.agent.model.Interview;
import com.agent.model.User;
import com.agent.repository.InterviewRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class InterviewService {

    private final InterviewRepository repository;

    private final UserService userService;

    private final CompanyService companyService;

    public InterviewService(InterviewRepository repository, UserService userService, CompanyService companyService) {
        this.repository = repository;
        this.userService = userService;
        this.companyService = companyService;
    }


    public Interview addInterview(NewInterviewRequestDTO newInterviewDTO) {
        User candidate = userService.findById(newInterviewDTO.getCandidateId()).orElseThrow(UserNotFoundException::new);
        Company company = companyService.findById(newInterviewDTO.getCompanyId()).orElseThrow(CompanyNotFoundException::new);
        Interview newInterview = new Interview(UUID.randomUUID().toString(),
                newInterviewDTO.getTitle(),
                newInterviewDTO.getHrInterview(),
                newInterviewDTO.getTechInterview(),
                newInterviewDTO.getPosition(),
                new Date(), candidate, company);
        return repository.save(newInterview);
    }

    public List<Interview> getCompanyInterviews(String id) {
        Company company = companyService.findById(id).orElseThrow(CompanyNotFoundException::new);
        return repository.findByCompany(company);
    }
}
