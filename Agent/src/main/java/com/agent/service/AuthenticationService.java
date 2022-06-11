package com.agent.service;

import com.agent.dto.TokenDTO;
import com.agent.exception.TokenExpiredException;
import com.agent.exception.TokenNotFoundException;
import com.agent.model.User;
import com.agent.model.VerificationToken;
import com.agent.security.util.TokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final TokenUtils tokenUtils;

    private final CompanyService companyService;

    private final VerificationTokenService verificationTokenService;

    private final UserService userService;

    private final int REGISTRATION_TOKEN_EXPIRES = 60;
    private final int RECOVERY_TOKEN_EXPIRES = 60;

    public AuthenticationService(AuthenticationManager authenticationManager, TokenUtils tokenUtils, CompanyService companyService, VerificationTokenService verificationTokenService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
        this.companyService = companyService;
        this.verificationTokenService = verificationTokenService;
        this.userService = userService;
    }

    public TokenDTO login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        return new TokenDTO(getToken(user), getRefreshToken(user));
    }

    private String getToken(User user) {
        return tokenUtils.generateToken(user.getRoles().get(0).getName(), user.getUsername(), user.getId(), false, companyService.getCompanyIdByUser(user.getId()));
    }

    private String getRefreshToken(User user) {
        return tokenUtils.generateToken(user.getRoles().get(0).getName(), user.getUsername(), user.getId(), true, companyService.getCompanyIdByUser(user.getId()));
    }


    public void verifyUserAccount(String token) {
        VerificationToken verificationToken = verificationTokenService.findVerificationTokenByToken(token);
        if (verificationToken == null) {
            throw new TokenNotFoundException();
        }
        User user = verificationToken.getUser();

        verificationTokenService.delete(verificationToken);

        if (getDifferenceInMinutes(verificationToken) < REGISTRATION_TOKEN_EXPIRES) {
            userService.activateUser(user);
        } else {
            throw new TokenExpiredException();
        }
    }


    private long getDifferenceInMinutes(VerificationToken verificationToken) {
        LocalDateTime tokenCreated = LocalDateTime.ofInstant(verificationToken.getCreatedDateTime().toInstant(), ZoneId.systemDefault());
        Long differenceInMinutes = ChronoUnit.MINUTES.between(tokenCreated, LocalDateTime.now());
        return differenceInMinutes;
    }
}
