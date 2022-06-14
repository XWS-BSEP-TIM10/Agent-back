package com.agent.controller;

import com.agent.dto.*;
import com.agent.exception.CodeNotMatchingException;
import com.agent.exception.TokenExpiredException;
import com.agent.exception.TokenNotFoundException;
import com.agent.exception.UserNotFoundException;
import com.agent.service.AuthenticationService;
import com.agent.service.LoggerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final LoggerService loggerService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        this.loggerService = new LoggerService(this.getClass());
    }

    @PostMapping(value = "/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO loginDTO, HttpServletRequest request) {
        try {
            TokenDTO tokenDTO = authenticationService.login(loginDTO.getEmail(), loginDTO.getPassword(), loginDTO.getCode());
            loggerService.loginSuccess(loginDTO.getEmail(), request.getRemoteAddr());
            return ResponseEntity.ok(tokenDTO);
        } catch (CodeNotMatchingException codeNotMatchingException) {
            loggerService.login2FAFailedCodeNotMatching(loginDTO.getEmail(), request.getRemoteAddr());
            return ResponseEntity.status(300).build();
        } catch (Exception ex) {
            loggerService.loginFailed(loginDTO.getEmail(), request.getRemoteAddr());
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasAuthority('UPDATE_2FA_STATUS')")
    @PutMapping(value = "/2fa")
    public ResponseEntity<TwoFAResponseDTO> change2FAStatus(@RequestBody TwoFADTO twoFADTO) {
        String secret = authenticationService.change2FAStatus(twoFADTO.getUserId(), twoFADTO.isEnable2FA());
        loggerService.twoFAStatusChanged(twoFADTO.isEnable2FA(), SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(new TwoFAResponseDTO(secret));
    }

    @PreAuthorize("hasAuthority('CHECK_2FA_STATUS')")
    @GetMapping(value= "/2fa/status/{userId}")
    public ResponseEntity<TwoFAStatusDTO> check2FAStatus(@PathVariable String userId, HttpServletRequest request) {
        try {
            boolean twoFAEnabled = authenticationService.checkTwoFaStatus(userId);
            loggerService.twoFAStatusCheck(SecurityContextHolder.getContext().getAuthentication().getName(), request.getRemoteAddr());
            return ResponseEntity.ok(new TwoFAStatusDTO(twoFAEnabled));
        } catch (UserNotFoundException ex) {
            loggerService.two2FACheckFailed(SecurityContextHolder.getContext().getAuthentication().getName(), request.getRemoteAddr());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/confirm/{token}")
    public ResponseEntity<HttpStatus> confirmToken(@PathVariable String token) {
        try {
            authenticationService.verifyUserAccount(token);
            return ResponseEntity.ok().build();
        } catch (TokenExpiredException ex) {
            return ResponseEntity.badRequest().build();
        } catch (TokenNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/recover")
    public ResponseEntity<?> recoverAccount(@Email String email) {
        try {
            authenticationService.recoverAccount(email);
            loggerService.accountRecovered(email);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException ex) {
            loggerService.accountRecoverFailedUserNotFound(email);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/checkToken/{token}")
    public ResponseEntity<?> checkToken(@PathVariable String token) {
        boolean valid = authenticationService.checkToken(token);
        if (!valid) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/recover/changePassword/{token}")
    public ResponseEntity<?> changePasswordRecovery(@PathVariable String token, @RequestBody PasswordDto passwordDto) {
        if (!passwordDto.getNewPassword().equals(passwordDto.getRepeatedNewPassword()))
            return ResponseEntity.badRequest().body("Passwords not matching");
        try {
            authenticationService.changePasswordRecovery(passwordDto.getNewPassword(), token);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Token expired");
        }
    }

    @GetMapping(value = "/password-less")
    public ResponseEntity<?> passwordlessToken(@RequestParam @Valid @Email String email) {
        try {
            authenticationService.generatePasswordlessToken(email);
            loggerService.generatePasswordlessLogin(email);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException ex) {
            loggerService.generatePasswordlessLoginFailed(ex.getMessage(), email);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/login/password-less/{token}")
    public ResponseEntity<?> passwordlessLogin(@PathVariable String token, HttpServletRequest request) {
        try {
            TokenDTO tokens = authenticationService.passwordlessLogin(token);
            loggerService.passwordlessLoginSuccess(SecurityContextHolder.getContext().getAuthentication().getName(), request.getRemoteAddr());
            return ResponseEntity.ok(tokens);
        } catch (TokenExpiredException ex) {
            loggerService.passwordlessLoginFailed(request.getRemoteAddr());
            return ResponseEntity.badRequest().body("Token expired");
        } catch (TokenNotFoundException | UserNotFoundException ex) {
            loggerService.passwordlessLoginFailed(request.getRemoteAddr());
            return ResponseEntity.notFound().build();
        }
    }
}
