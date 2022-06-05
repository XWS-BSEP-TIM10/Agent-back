package com.agent.service;

import com.agent.dto.CreateJobAdRequestDTO;
import com.agent.dto.CreateJobAdResponseDTO;
import com.agent.dto.GetJobAdDTO;
import com.agent.dto.ShareJobAdDTO;
import com.agent.exception.CompanyNotFoundException;
import com.agent.exception.JobAdNotFoundException;
import com.agent.model.APIToken;
import com.agent.model.Company;
import com.agent.model.JobAd;
import com.agent.model.Requirement;
import com.agent.repository.JobAdRepository;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JobAdService {

    private final JobAdRepository repo;
    private final CompanyService companyService;
    private final RequirementService requirementService;
    private final APITokenService apiTokenService;
    RestTemplate template = new RestTemplate();

    public JobAdService(JobAdRepository repo, CompanyService companyService, RequirementService requirementService, APITokenService apiTokenService) {
        this.repo = repo;
        this.companyService = companyService;
        this.requirementService = requirementService;
        this.apiTokenService = apiTokenService;
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

    public ShareJobAdDTO shareJobAd(String id, String userId) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        RestTemplate restTemplate = createRestTemplate();

        Optional<JobAd> jobAd = repo.findById(id);
        if(jobAd.isEmpty()) return null;
        ShareJobAdDTO shareJobAd = new ShareJobAdDTO(jobAd.get());

        HttpHeaders headers = new HttpHeaders();
        APIToken apiToken = apiTokenService.findByUser(userId);
        if(apiToken == null) return null;
        headers.set("DislinktAuth", apiToken.getToken());

        HttpEntity<ShareJobAdDTO> requestEntity =
                new HttpEntity<>(shareJobAd, headers);
        return restTemplate.exchange("https://localhost:8678/api/v1/job-ads", HttpMethod.POST, requestEntity, ShareJobAdDTO.class).getBody();
    }

    @NotNull
    private RestTemplate createRestTemplate() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                NoopHostnameVerifier.INSTANCE);

        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory> create()
                        .register("https", sslsf)
                        .register("http", new PlainConnectionSocketFactory())
                        .build();

        BasicHttpClientConnectionManager connectionManager =
                new BasicHttpClientConnectionManager(socketFactoryRegistry);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
                .setConnectionManager(connectionManager).build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(requestFactory);
    }


}
