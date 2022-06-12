package com.agent.controller;


import com.agent.dto.CreateJobAdRequestDTO;
import com.agent.dto.CreateJobAdResponseDTO;
import com.agent.dto.GetJobAdDTO;
import com.agent.dto.ShareJobAdDTO;
import com.agent.exception.APITokenNotFoundException;
import com.agent.exception.CompanyNotFoundException;
import com.agent.exception.JobAdNotFoundException;
import com.agent.security.util.TokenUtils;
import com.agent.service.JobAdService;
import com.agent.service.LoggerService;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class JobAdController {

    private final JobAdService jobAdService;

    private final TokenUtils tokenUtils;

    private final LoggerService loggerService;

    public JobAdController(JobAdService jobAdService, TokenUtils tokenUtils) {
        this.jobAdService = jobAdService;
        this.tokenUtils = tokenUtils;
        this.loggerService = new LoggerService(this.getClass());
    }

    @PreAuthorize("hasAuthority('CREATE_JOB_AD_PERMISSION')")
    @PostMapping("job-ads")
    public ResponseEntity<CreateJobAdResponseDTO> addJobAd(@Valid @RequestBody CreateJobAdRequestDTO createDTO) {
        try {
            CreateJobAdResponseDTO responseDTO = jobAdService.createJobAd(createDTO);
            loggerService.jobAdCreated(SecurityContextHolder.getContext().getAuthentication().getName());
            return ResponseEntity.ok(responseDTO);
        } catch (CompanyNotFoundException exception) {
            loggerService.jobAdCreatingFailed(exception.getMessage(), SecurityContextHolder.getContext().getAuthentication().getName());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("companies/{id}/job-ads")
    public ResponseEntity<List<GetJobAdDTO>> getJobAd(@PathVariable String id) {
        try {
            List<GetJobAdDTO> responseDTO = jobAdService.getJobAdsForCompany(id);
            return ResponseEntity.ok(responseDTO);
        } catch (CompanyNotFoundException exception) {
            loggerService.jobAdsGettingFailed(exception.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('DELETE_JOB_AD_PERMISSION')")
    @DeleteMapping("job-ads/{id}")
    public ResponseEntity<HttpStatus> deleteJobAd(@PathVariable String id) {
        try {
            jobAdService.deleteJobAd(id);
            loggerService.jobAdDeleted(id, SecurityContextHolder.getContext().getAuthentication().getName());
            return ResponseEntity.noContent().build();
        } catch (JobAdNotFoundException jobAdNotFoundException) {
            loggerService.jobAdDeletingFailed(id, SecurityContextHolder.getContext().getAuthentication().getName());
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('SHARE_JOB_AD_PERMISSION')")
    @PostMapping("job-ads/{id}")
    public ResponseEntity<ShareJobAdDTO> shareJobAd(@PathVariable String id, @RequestHeader(value = "Authorization") String jwtToken) {
        try {
            String userId = tokenUtils.getUserIdFromToken(jwtToken.substring(7));
            ShareJobAdDTO sharedJobAd = jobAdService.shareJobAd(id, userId);
            if (sharedJobAd == null) {
                loggerService.jobAdSharingFailed(id, "Job ad not found", SecurityContextHolder.getContext().getAuthentication().getName());
                return ResponseEntity.notFound().build();
            }
            loggerService.jobAdShared(id, SecurityContextHolder.getContext().getAuthentication().getName());
            return ResponseEntity.ok(sharedJobAd);
        } catch (APITokenNotFoundException e) {
            loggerService.jobAdSharingFailed(id, e.getMessage(), SecurityContextHolder.getContext().getAuthentication().getName());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            loggerService.jobAdSharingFailed(id, e.getMessage(), SecurityContextHolder.getContext().getAuthentication().getName());
            return ResponseEntity.internalServerError().build();
        }
    }
}
