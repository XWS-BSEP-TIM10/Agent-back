package com.agent.controller;

import com.agent.dto.InterviewDTO;
import com.agent.dto.NewInterviewRequestDTO;
import com.agent.dto.NewInterviewResponseDTO;
import com.agent.exception.CompanyNotFoundException;
import com.agent.exception.UserNotFoundException;
import com.agent.model.Interview;
import com.agent.service.InterviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping("interviews")
    public ResponseEntity<NewInterviewResponseDTO> addInterview(@Valid @RequestBody NewInterviewRequestDTO newInterviewDTO) {
        try {
            Interview interview = interviewService.addInterview(newInterviewDTO);
            if (interview == null)
                return ResponseEntity.internalServerError().build();
            return ResponseEntity.ok(new NewInterviewResponseDTO(interview));
        } catch (UserNotFoundException | CompanyNotFoundException exception) {
            exception.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("companies/{id}/interviews")
    public ResponseEntity<List<InterviewDTO>> getCompanyReview(@PathVariable String id) {
        try {
            List<Interview> interviews = interviewService.getCompanyInterviews(id);
            List<InterviewDTO> dtos = new ArrayList<>();
            for (Interview interview : interviews)
                dtos.add(new InterviewDTO(interview));
            return ResponseEntity.ok(dtos);
        } catch (CompanyNotFoundException exception) {
            exception.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
