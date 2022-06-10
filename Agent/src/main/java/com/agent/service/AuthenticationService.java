package com.agent.service;

import com.agent.dto.TokenDTO;
import com.agent.model.User;
import com.agent.security.util.TokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final TokenUtils tokenUtils;
    
    private final CompanyService companyService;

    public AuthenticationService(AuthenticationManager authenticationManager, TokenUtils tokenUtils,CompanyService companyService) {
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
        this.companyService = companyService;
    }

    public TokenDTO login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        return new TokenDTO(getToken(user), getRefreshToken(user));
    }

    private String getToken(User user) {
        return tokenUtils.generateToken(user.getRoles().get(0).getName(), user.getUsername(),false,companyService.getCompanyIdByUser(user.getId()));
    }

    private String getRefreshToken(User user) {
        return tokenUtils.generateToken(user.getRoles().get(0).getName(), user.getUsername(), true,companyService.getCompanyIdByUser(user.getId()));
    }
}
