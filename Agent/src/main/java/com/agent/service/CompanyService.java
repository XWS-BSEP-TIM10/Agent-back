package com.agent.service;

import com.agent.dto.CreateCompanyRequestDTO;
import com.agent.dto.CreateCompanyResponseDTO;
import com.agent.dto.GetCompanyResponseDTO;
import com.agent.exception.CompanyNotFoundException;
import com.agent.exception.UserNotFoundException;
import com.agent.model.Company;
import com.agent.model.User;
import com.agent.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public CreateCompanyResponseDTO updateCompany(String id, CreateCompanyRequestDTO updateDTO) {
        Company company = this.companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
        company.setName(updateDTO.getName());
        company.setAddress(updateDTO.getAddress());
        company.setWebsite(updateDTO.getWebsite());
        company.setPhoneNumber(updateDTO.getPhoneNumber());
        company.setEmail(updateDTO.getEmail());
        company.setDescription(updateDTO.getDescription());
        Company updatedCompany = companyRepository.save(company);
        return new CreateCompanyResponseDTO(updatedCompany);
    }

    public List<GetCompanyResponseDTO> getCompanies(boolean activated) {
        List<Company> companies = companyRepository.getCompanies(activated);
        return companies.stream().map(GetCompanyResponseDTO::new).toList();
    }

    public void activateCompany(String id) {
        Company company = this.companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
        company.setActivated(true);
        User user = this.userService.findById(company.getOwner().getId()).orElseThrow(UserNotFoundException::new);
        userService.updateUserRole(user, "ROLE_COMPANY_OWNER");
        companyRepository.save(company);
    }

    public Optional<Company> findById(String id) {
        return companyRepository.findById(id);
    }

    public void save(Company company) {
        companyRepository.save(company);
    }
}
