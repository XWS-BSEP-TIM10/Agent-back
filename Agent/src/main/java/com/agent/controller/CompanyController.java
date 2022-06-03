package com.agent.controller;

import com.agent.dto.CreateCompanyRequestDTO;
import com.agent.dto.CreateCompanyResponseDTO;
import com.agent.dto.GetCompanyResponseDTO;
import com.agent.exception.CompanyNotFoundException;
import com.agent.exception.UserNotFoundException;
import com.agent.repository.CompanyRepository;
import com.agent.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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

    @PutMapping("{id}")
    public ResponseEntity<CreateCompanyResponseDTO> updateCompany(@Valid @RequestBody CreateCompanyRequestDTO updateDTO, @PathVariable String id) {
        try {
            CreateCompanyResponseDTO responseDTO = companyService.updateCompany(id, updateDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (CompanyNotFoundException notFoundException) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<GetCompanyResponseDTO>> getCompanies(@RequestParam(required = true) boolean activated) {
        List<GetCompanyResponseDTO> companyResponseDTOS = companyService.getCompanies(activated);
        return ResponseEntity.ok(companyResponseDTOS);
    }

    @PutMapping("{id}/activate")
    public ResponseEntity<HttpStatus> activateCompany(@PathVariable String id) {
        try {
            companyService.activateCompany(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException | CompanyNotFoundException notFoundException) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<List<GetCompanyResponseDTO>> getCompanyById(@PathVariable String id) {
        if(companyService.findById(id).isPresent()) {
            List<GetCompanyResponseDTO> companyResponseDTOS = new ArrayList<>();
            companyResponseDTOS.add(new GetCompanyResponseDTO(companyService.findById(id).get()));
            return ResponseEntity.ok(companyResponseDTOS);
        }
        return ResponseEntity.notFound().build();
    }
}
