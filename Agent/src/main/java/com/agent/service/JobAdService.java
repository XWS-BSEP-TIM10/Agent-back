package com.agent.service;

import com.agent.dto.CreateJobAdRequestDTO;
import com.agent.dto.CreateJobAdResponseDTO;
import com.agent.dto.GetJobAdDTO;
import com.agent.dto.ShareJobAdDTO;
import com.agent.exception.CompanyNotFoundException;
import com.agent.exception.JobAdNotFoundException;
import com.agent.model.Company;
import com.agent.model.JobAd;
import com.agent.model.Requirement;
import com.agent.repository.JobAdRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JobAdService {

    private final JobAdRepository repo;
    private final CompanyService companyService;
    private final RequirementService requirementService;
    RestTemplate template = new RestTemplate();

    public JobAdService(JobAdRepository repo, CompanyService companyService, RequirementService requirementService) {
        this.repo = repo;
        this.companyService = companyService;
        this.requirementService = requirementService;
    }

    public CreateJobAdResponseDTO createJobAd(CreateJobAdRequestDTO createDTO) {
        Company company = companyService.findById(createDTO.getCompanyId()).orElseThrow(CompanyNotFoundException::new);
        JobAd newJobAd = new JobAd(UUID.randomUUID().toString(), createDTO.getTitle(), createDTO.getPosition(), createDTO.getDescription(), company);

        createDTO.getRequirements().forEach(requirementName -> {
            Optional<Requirement> requirementOptional = requirementService.findByName(requirementName);
            if (requirementOptional.isPresent()) {
                newJobAd.getRequirements().add(requirementOptional.get());
            } else {
                newJobAd.getRequirements().add(requirementService.create(requirementName));
            }
        });
        return new CreateJobAdResponseDTO(repo.save(newJobAd));
    }

    public void deleteJobAd(String id) {
        if (!repo.existsById(id))
            throw new JobAdNotFoundException();
        repo.deleteById(id);
    }

    public List<GetJobAdDTO> getJobAdsForCompany(String id) {
        if (companyService.findById(id).isEmpty())
            throw new CompanyNotFoundException();
        return repo.findAllByCompanyId(id).stream().map(GetJobAdDTO::new).toList();
    }

    public ShareJobAdDTO shareJobAd(String id) {

        Optional<JobAd> jobAd = repo.findById(id);
        if(jobAd.isEmpty())
            return null;
        ShareJobAdDTO shareJobAd = new ShareJobAdDTO(jobAd.get());
        //ShareJobAdDTO payload = new ShareJobAdDTO();

        HttpHeaders headers = new HttpHeaders();
        // headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        //headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("DislinktAuth", "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzcHJpbmctc2VjdXJpdHktZXhhbXBsZSIsInN1YiI6ImQxMjYwMmZkLWI3YWYtNGRhMS1iMWNhLWJhZDgxNjZkMWZiNSIsInJvbGUiOiJST0xFX0FHRU5UIiwidXNlcklkIjoiZDEyNjAyZmQtYjdhZi00ZGExLWIxY2EtYmFkODE2NmQxZmIyIiwiYXVkIjoid2ViIiwiaWF0IjoxNjU0Mjc0MzM0LCJleHAiOjE2NTYwNzQzMzR9.DDoDuczkf53uMw-lVPtSJDeQZsmC_qR2gBm7548o1g4WX44DdRTrLCQKkaGIP61ONHj9mDNBiQVof7a34tphug");

        HttpEntity<ShareJobAdDTO> requestEntity =
                new HttpEntity<>(shareJobAd, headers);
        return template.postForObject("https://localhost:8678/api/v1/job-ads", requestEntity, ShareJobAdDTO.class);
    }

}
