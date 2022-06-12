package com.agent.controller;

import com.agent.dto.APITokenRequestDTO;
import com.agent.dto.APITokenResponseDTO;
import com.agent.model.APIToken;
import com.agent.security.util.TokenUtils;
import com.agent.service.APITokenService;
import com.agent.service.LoggerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/api-tokens")
public class APITokenController {

    private final APITokenService apiTokenService;

    private final TokenUtils tokenUtils;
    private final LoggerService loggerService;

    public APITokenController(APITokenService apiTokenService, TokenUtils tokenUtils) {
        this.apiTokenService = apiTokenService;
        this.tokenUtils = tokenUtils;
        this.loggerService = new LoggerService(this.getClass());
    }

    @PreAuthorize("hasAuthority('ADD_API_TOKEN_PERMISSION')")
    @PostMapping
    public ResponseEntity<APITokenResponseDTO> addAPIToken(@Valid @RequestBody APITokenRequestDTO apiTokenDTO, @RequestHeader(value = "Authorization") String jwtToken) {
        String userId = tokenUtils.getUsernameFromToken(jwtToken.substring(7));
        APIToken savedApiToken = apiTokenService.add(apiTokenDTO.getApiToken(), userId);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        loggerService.addedAPIToken(userDetails.getUsername());
        return ResponseEntity.ok(new APITokenResponseDTO(savedApiToken.getId(), savedApiToken.getToken()));
    }
}
