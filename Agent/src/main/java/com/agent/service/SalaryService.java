package com.agent.service;

import com.agent.dto.CreateSalaryResponseDTO;
import com.agent.dto.GetSalaryDTO;
import com.agent.exception.CompanyNotFoundException;
import com.agent.exception.UserNotFoundException;
import com.agent.model.Company;
import com.agent.model.Salary;
import com.agent.model.User;
import com.agent.repository.SalaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SalaryService {
    private final SalaryRepository repo;
    private final UserService userService;
    private final CompanyService companyService;


    public SalaryService(SalaryRepository repo, UserService userService, CompanyService companyService) {
        this.repo = repo;
        this.userService = userService;
        this.companyService = companyService;
    }

    public CreateSalaryResponseDTO createSalary(CreateSalaryResponseDTO createDTO) {
        Company company = companyService.findById(createDTO.getCompanyId()).orElseThrow(CompanyNotFoundException::new);
        User employee = userService.findById(createDTO.getEmployeeId()).orElseThrow(UserNotFoundException::new);

        Salary salary = new Salary(UUID.randomUUID().toString(), createDTO.getValue(), createDTO.getPosition(), employee, company);
        return new CreateSalaryResponseDTO(repo.save(salary));
    }

    public List<GetSalaryDTO> getSalaries(String id) {
        if (companyService.findById(id).isEmpty())
            throw new CompanyNotFoundException();
        return repo.findAllByCompanyId(id).stream().map(GetSalaryDTO::new).toList();
    }
}
