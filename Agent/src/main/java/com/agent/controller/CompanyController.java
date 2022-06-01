package com.agent.controller;

import com.agent.dto.CreateCompanyRequestDTO;
import com.agent.dto.CreateCompanyResponseDTO;
import com.agent.exception.UserNotFoundException;
import com.agent.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping()
    public ResponseEntity<CreateCompanyResponseDTO> addCompany(@Valid @RequestBody CreateCompanyRequestDTO createDTO) {
        try {
            CreateCompanyResponseDTO responseDTO = companyService.addCompany(createDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (UserNotFoundException userNotFoundException) {
            return ResponseEntity.notFound().build();
        }
    }
}
