package com.agent.controller;

import com.agent.dto.APITokenRequestDTO;
import com.agent.dto.APITokenResponseDTO;
import com.agent.exception.CompanyNotFoundException;
import com.agent.model.APIToken;
import com.agent.security.util.TokenUtils;
import com.agent.service.APITokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/api-tokens")
public class APITokenController {

    private final APITokenService apiTokenService;

    private final TokenUtils tokenUtils;

    public APITokenController(APITokenService apiTokenService, TokenUtils tokenUtils) {
        this.apiTokenService = apiTokenService;
        this.tokenUtils = tokenUtils;
    }

    @PostMapping
    public ResponseEntity<APITokenResponseDTO> addAPIToken(@Valid @RequestBody APITokenRequestDTO apiTokenDTO, @RequestHeader(value = "Authorization") String jwtToken) {
        String userId = tokenUtils.getUsernameFromToken(jwtToken.substring(7));
        APIToken savedApiToken = apiTokenService.add(apiTokenDTO.getApiToken(), userId);
        return ResponseEntity.ok(new APITokenResponseDTO(savedApiToken.getId(), savedApiToken.getToken()));
    }
}
