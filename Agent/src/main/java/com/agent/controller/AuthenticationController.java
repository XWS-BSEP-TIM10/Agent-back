package com.agent.controller;

import com.agent.dto.LoginDTO;
import com.agent.dto.PasswordDto;
import com.agent.dto.TokenDTO;
import com.agent.exception.RepeatedPasswordNotMatchingException;
import com.agent.exception.TokenExpiredException;
import com.agent.exception.TokenNotFoundException;
import com.agent.exception.UserNotFoundException;
import com.agent.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

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
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/checkToken/{token}")
    public ResponseEntity<?> checkToken(@PathVariable String token) {
        boolean valid = authenticationService.checkToken(token);
        if (!valid) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/recover/changePassword/{token}")
    public ResponseEntity<?> changePasswordRecovery(@PathVariable String token, @RequestBody PasswordDto passwordDto) {
        if(!passwordDto.getNewPassword().equals(passwordDto.getRepeatedNewPassword()))
            return ResponseEntity.badRequest().body("Passwords not matching");
        try {
            authenticationService.changePasswordRecovery(passwordDto.getNewPassword(), token);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Token expired");
        }
    }

    @GetMapping(value = "/password-less")
    public ResponseEntity<?> passwordLessToken(@Email String email) {
        try {
            authenticationService.generatePasswordLessToken(email);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/login/password-less/{token}")
    public ResponseEntity<?> passwordLessLogin(@PathVariable String token) {
        try {
            TokenDTO tokens = authenticationService.passwordLessLogin(token);
            return ResponseEntity.ok(tokens);
        } catch (TokenExpiredException ex) {
            return ResponseEntity.badRequest().body("Token expired");
        } catch (TokenNotFoundException | UserNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }

    }
}
