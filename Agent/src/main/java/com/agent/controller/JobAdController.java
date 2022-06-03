package com.agent.controller;


import com.agent.dto.CreateJobAdRequestDTO;
import com.agent.dto.CreateJobAdResponseDTO;
import com.agent.dto.GetJobAdDTO;
import com.agent.dto.ShareJobAdDTO;
import com.agent.exception.CompanyNotFoundException;
import com.agent.exception.JobAdNotFoundException;
import com.agent.service.JobAdService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class JobAdController {

    private final JobAdService jobAdService;

    public JobAdController(JobAdService jobAdService) {
        this.jobAdService = jobAdService;
    }

    @PostMapping("job-ads")
    public ResponseEntity<CreateJobAdResponseDTO> addJobAd(@Valid @RequestBody CreateJobAdRequestDTO createDTO) {
        try {
            CreateJobAdResponseDTO responseDTO = jobAdService.createJobAd(createDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (CompanyNotFoundException companyNotFoundException) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("companies/{id}/job-ads")
    public ResponseEntity<List<GetJobAdDTO>> getJobAd(@PathVariable String id) {
        try {
            List<GetJobAdDTO> responseDTO = jobAdService.getJobAdsForCompany(id);
            return ResponseEntity.ok(responseDTO);
        } catch (CompanyNotFoundException companyNotFoundException) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("job-ads/{id}")
    public ResponseEntity<HttpStatus> deleteJobAd(@PathVariable String id) {
        try {
            jobAdService.deleteJobAd(id);
            return ResponseEntity.noContent().build();
        } catch (JobAdNotFoundException jobAdNotFoundException) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("job-ads/{id}")
    public ResponseEntity<ShareJobAdDTO> shareJobAd(@PathVariable String id) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        ShareJobAdDTO sharedJobAd = jobAdService.shareJobAd(id);
        if(sharedJobAd == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(sharedJobAd);
    }
}
