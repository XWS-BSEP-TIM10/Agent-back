package com.agent.controller;

import com.agent.dto.LoginDTO;
import com.agent.dto.TokenDTO;
import com.agent.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO loginDTO) {

        try {
            TokenDTO tokenDTO = authenticationService.login(loginDTO.getEmail(), loginDTO.getPassword());
            return ResponseEntity.ok(tokenDTO);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
