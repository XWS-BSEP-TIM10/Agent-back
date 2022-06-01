package com.agent.service;

import com.agent.dto.CreateCompanyRequestDTO;
import com.agent.dto.CreateCompanyResponseDTO;
import com.agent.exception.UserNotFoundException;
import com.agent.model.Company;
import com.agent.model.User;
import com.agent.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserService userService;

    public CompanyService(CompanyRepository companyRepository, UserService userService) {
        this.companyRepository = companyRepository;
        this.userService = userService;
    }

    public CreateCompanyResponseDTO addCompany(CreateCompanyRequestDTO createDTO) {
        User user = this.userService.findById(createDTO.getOwnerId()).orElseThrow(UserNotFoundException::new);
        Company company = new Company(UUID.randomUUID().toString(),
                createDTO.getName(),
                createDTO.getAddress(),
                createDTO.getWebsite(),
                createDTO.getPhoneNumber(),
                createDTO.getEmail(),
                createDTO.getDescription(),
                user);
        Company newCompany = companyRepository.save(company);
        return new CreateCompanyResponseDTO(newCompany);
    }
}
