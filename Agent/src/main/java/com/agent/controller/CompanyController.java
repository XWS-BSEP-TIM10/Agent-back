package com.agent.controller;

import com.agent.dto.CreateCompanyRequestDTO;
import com.agent.dto.CreateCompanyResponseDTO;
import com.agent.dto.GetCompanyResponseDTO;
import com.agent.exception.CompanyNotFoundException;
import com.agent.exception.UserNotFoundException;
import com.agent.model.Company;
import com.agent.service.CompanyService;
import com.agent.service.LoggerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;

    private final LoggerService loggerService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
        this.loggerService = new LoggerService(this.getClass());
    }

    @PreAuthorize("hasAuthority('CREATE_COMPANY_PERMISSION')")
    @PostMapping()
    public ResponseEntity<CreateCompanyResponseDTO> addCompany(@Valid @RequestBody CreateCompanyRequestDTO createDTO) {
        try {
            CreateCompanyResponseDTO responseDTO = companyService.addCompany(createDTO);
            loggerService.createCompanySuccess(SecurityContextHolder.getContext().getAuthentication().getName());
            return ResponseEntity.ok(responseDTO);
        } catch (UserNotFoundException userNotFoundException) {
            loggerService.createCompanyFailure(SecurityContextHolder.getContext().getAuthentication().getName());
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('UPDATE_COMPANY_PERMISSION')")
    @PutMapping("{id}")
    public ResponseEntity<CreateCompanyResponseDTO> updateCompany(@Valid @RequestBody CreateCompanyRequestDTO updateDTO, @PathVariable String id) {
        try {
            CreateCompanyResponseDTO responseDTO = companyService.updateCompany(id, updateDTO);
            loggerService.updateCompanySuccess(SecurityContextHolder.getContext().getAuthentication().getName());
            return ResponseEntity.ok(responseDTO);
        } catch (CompanyNotFoundException notFoundException) {
            loggerService.updateCompanyFailure(SecurityContextHolder.getContext().getAuthentication().getName());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<GetCompanyResponseDTO>> getCompanies(@RequestParam(required = true) boolean activated) {
        List<GetCompanyResponseDTO> companyResponseDTOS = companyService.getCompanies(activated);
        return ResponseEntity.ok(companyResponseDTOS);
    }

    @PreAuthorize("hasAuthority('ACTIVATE_COMPANY_PERMISSION')")
    @PutMapping("{id}/activate")
    public ResponseEntity<HttpStatus> activateCompany(@PathVariable String id) {
        try {
            companyService.activateCompany(id);
            loggerService.activateCompanySuccess(SecurityContextHolder.getContext().getAuthentication().getName(), id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException | CompanyNotFoundException notFoundException) {
            loggerService.activateCompanyFailure(SecurityContextHolder.getContext().getAuthentication().getName(), id);
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('REMOVE_COMPANY_PERMISSION')")
    @DeleteMapping("{id}/remove")
    public ResponseEntity<HttpStatus> removeCompany(@PathVariable String id) {
        try {
            companyService.removeCompany(id);
            loggerService.removeCompanySuccess(SecurityContextHolder.getContext().getAuthentication().getName(), id);
            return ResponseEntity.noContent().build();
        } catch (CompanyNotFoundException notFoundException) {
            loggerService.removeCompanyFailure(SecurityContextHolder.getContext().getAuthentication().getName(), id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<GetCompanyResponseDTO> getCompanyById(@PathVariable String id) {
        Optional<Company> companyOptional = companyService.findById(id);
        if (companyOptional.isPresent()) {
            GetCompanyResponseDTO companyResponseDTO = new GetCompanyResponseDTO(companyOptional.get());
            return ResponseEntity.ok(companyResponseDTO);
        }
        return ResponseEntity.notFound().build();
    }
}
