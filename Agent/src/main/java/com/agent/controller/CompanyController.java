package com.agent.controller;

import com.agent.dto.CreateCompanyRequestDTO;
import com.agent.dto.CreateCompanyResponseDTO;
import com.agent.dto.GetCompanyResponseDTO;
import com.agent.exception.CompanyNotFoundException;
import com.agent.exception.UserNotFoundException;
import com.agent.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @DeleteMapping("{id}/remove")
    public ResponseEntity<HttpStatus> removeCompany(@PathVariable String id) {
        try {
            companyService.removeCompany(id);
            return ResponseEntity.noContent().build();
        } catch (CompanyNotFoundException notFoundException) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<GetCompanyResponseDTO> getCompanyById(@PathVariable String id) {
        if(companyService.findById(id).isPresent()) {
            GetCompanyResponseDTO companyResponseDTO = new GetCompanyResponseDTO(companyService.findById(id).get());
            return ResponseEntity.ok(companyResponseDTO);
        }
        return ResponseEntity.notFound().build();
    }
}
