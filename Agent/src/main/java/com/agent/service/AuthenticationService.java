package com.agent.service;

import com.agent.dto.TokenDTO;
import com.agent.exception.CodeNotMatchingException;
import com.agent.exception.TokenExpiredException;
import com.agent.exception.TokenNotFoundException;
import com.agent.exception.UserNotFoundException;
import com.agent.model.User;
import com.agent.model.VerificationToken;
import com.agent.security.util.TokenUtils;
import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final TokenUtils tokenUtils;

    private final CompanyService companyService;

    private final VerificationTokenService verificationTokenService;

    private final UserService userService;

    private final EmailService emailService;

    private final LoggerService loggerService;

    private final int REGISTRATION_TOKEN_EXPIRES = 60;
    private final int RECOVERY_TOKEN_EXPIRES = 60;

    public AuthenticationService(AuthenticationManager authenticationManager, TokenUtils tokenUtils, CompanyService companyService, VerificationTokenService verificationTokenService, UserService userService, EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
        this.companyService = companyService;
        this.verificationTokenService = verificationTokenService;
        this.userService = userService;
        this.emailService = emailService;
        this.loggerService = new LoggerService(this.getClass());
    }

    public TokenDTO login(String email, String password, String code) {
        User user = userService.findByEmail(email).orElseThrow(RuntimeException::new);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                email, password));

        if (user.isUsing2FA() && (code == null || !code.equals(getTOTPCode(user.getSecret())))) {
            throw new CodeNotMatchingException();
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User loggedUser = (User) authentication.getPrincipal();
        return new TokenDTO(getToken(loggedUser), getRefreshToken(loggedUser));
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
            loggerService.accountConfirmedFailedTokenNotFound(token);
            throw new TokenNotFoundException();
        }
        User user = verificationToken.getUser();
        verificationTokenService.delete(verificationToken);
        if (getDifferenceInMinutes(verificationToken) < REGISTRATION_TOKEN_EXPIRES) {
            userService.activateUser(user);
            loggerService.accountConfirmed(user.getEmail());
        } else {
            loggerService.accountConfirmedFailedTokenExpired(token, user.getEmail());
            throw new TokenExpiredException();
        }
    }


    private long getDifferenceInMinutes(VerificationToken verificationToken) {
        LocalDateTime tokenCreated = LocalDateTime.ofInstant(verificationToken.getCreatedDateTime().toInstant(), ZoneId.systemDefault());
        return ChronoUnit.MINUTES.between(tokenCreated, LocalDateTime.now());
    }

    public void recoverAccount(String email) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty())
            throw new UserNotFoundException();
        VerificationToken verificationToken = verificationTokenService.generateVerificationToken(user.get());
        emailService.sendEmail(email, "Account recovery", "https://localhost:4201/recover/" + verificationToken.getToken() + " Click on this link to change your password");
    }

    public boolean checkToken(String token) {
        VerificationToken verificationToken = verificationTokenService.findVerificationTokenByToken(token);
        return verificationToken != null && getDifferenceInMinutes(verificationToken) < RECOVERY_TOKEN_EXPIRES;
    }

    public void changePasswordRecovery(String newPassword, String token) throws TokenExpiredException, UserNotFoundException {
        VerificationToken verificationToken = getVerificationToken(token);
        Optional<User> user = userService.findByEmail(verificationToken.getUser().getEmail());
        if (user.isEmpty())
            throw new UserNotFoundException();
        userService.changePassword(user.get(), newPassword);
        loggerService.passwordRecoveredSuccessfully(user.get().getEmail());
        verificationTokenService.delete(verificationToken);
    }

    public void generatePasswordlessToken(String email) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty())
            throw new UserNotFoundException();
        VerificationToken verificationToken = verificationTokenService.generateVerificationToken(user.get());
        emailService.sendEmail(email, "Passwordless login", "https://localhost:4201/login/password-less/" + verificationToken.getToken() + " Click on this link to sign in");

    }

    public TokenDTO passwordlessLogin(String token) {

        VerificationToken verificationToken = getVerificationToken(token);
        Optional<User> user = userService.findByEmail(verificationToken.getUser().getEmail());
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.get().getUsername(), null, user.get().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        verificationTokenService.delete(verificationToken);
        return new TokenDTO(getToken(user.get()), getRefreshToken(user.get()));
    }

    public VerificationToken getVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenService.findVerificationTokenByToken(token);
        if (verificationToken == null)
            throw new TokenNotFoundException();
        if (getDifferenceInMinutes(verificationToken) >= RECOVERY_TOKEN_EXPIRES)
            throw new TokenExpiredException();
        return verificationToken;
    }

    public static String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }

    public String change2FAStatus(String userId, boolean enableFA) {
        return userService.change2FAStatus(userId, enableFA);
    }

    public boolean checkTwoFaStatus(String userId) {
        User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        return user.isUsing2FA();
    }
}
