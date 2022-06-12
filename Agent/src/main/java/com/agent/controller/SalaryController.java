package com.agent.controller;


import com.agent.dto.CreateSalaryResponseDTO;
import com.agent.dto.GetSalaryDTO;
import com.agent.exception.CompanyNotFoundException;
import com.agent.exception.UserNotFoundException;
import com.agent.service.LoggerService;
import com.agent.service.SalaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class SalaryController {

    private final SalaryService salaryService;

    private final LoggerService loggerService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
        this.loggerService = new LoggerService(this.getClass());
    }

    @PreAuthorize("hasAuthority('CREATE_SALARY_PERMISSION')")
    @PostMapping("salaries")
    public ResponseEntity<CreateSalaryResponseDTO> createSalary(@Valid @RequestBody CreateSalaryResponseDTO createDTO) {
        try {
            CreateSalaryResponseDTO responseDTO = salaryService.createSalary(createDTO);
            loggerService.salaryCreated(SecurityContextHolder.getContext().getAuthentication().getName());
            return ResponseEntity.ok(responseDTO);
        } catch (UserNotFoundException | CompanyNotFoundException exception) {
            loggerService.salaryCreatingFailed(exception.getMessage(), SecurityContextHolder.getContext().getAuthentication().getName());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("companies/{id}/salaries")
    public ResponseEntity<List<GetSalaryDTO>> getSalaries(@PathVariable String id) {
        try {
            List<GetSalaryDTO> responseDTOs = salaryService.getSalaries(id);
            return ResponseEntity.ok(responseDTOs);
        } catch (CompanyNotFoundException exception) {
            loggerService.salariesGettingFailed(exception.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}
