package com.agent.controller;


import com.agent.dto.CreateJobAdRequestDTO;
import com.agent.dto.CreateJobAdResponseDTO;
import com.agent.dto.GetJobAdDTO;
import com.agent.dto.ShareJobAdDTO;
import com.agent.exception.CompanyNotFoundException;
import com.agent.exception.JobAdNotFoundException;
import com.agent.security.util.TokenUtils;
import com.agent.service.JobAdService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class JobAdController {

    private final JobAdService jobAdService;

    private final TokenUtils tokenUtils;

    public JobAdController(JobAdService jobAdService, TokenUtils tokenUtils) {
        this.jobAdService = jobAdService;
        this.tokenUtils = tokenUtils;
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
    public ResponseEntity<ShareJobAdDTO> shareJobAd(@PathVariable String id, @RequestHeader(value = "Authorization") String jwtToken) {
        try {
            String userId = tokenUtils.getUsernameFromToken(jwtToken.substring(7));
            ShareJobAdDTO sharedJobAd = jobAdService.shareJobAd(id, userId);
            if (sharedJobAd == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(sharedJobAd);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
