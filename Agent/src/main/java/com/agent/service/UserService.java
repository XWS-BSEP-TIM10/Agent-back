package com.agent.service;

import com.agent.dto.ChangePasswordDTO;
import com.agent.exception.UserAlreadyExistsException;
import com.agent.exception.UserNotFoundException;
import com.agent.exception.WrongPasswordException;
import com.agent.model.Role;
import com.agent.model.User;
import com.agent.model.VerificationToken;
import com.agent.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final VerificationTokenService verificationTokenService;

    public UserService(UserRepository repository, RoleService roleService, PasswordEncoder passwordEncoder, EmailService emailService, VerificationTokenService verificationTokenService) {
        this.repository = repository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.verificationTokenService = verificationTokenService;
    }

    public Optional<User> findById(String id) {
        return repository.findById(id);
    }


    public User addNewUser(User user) throws UserAlreadyExistsException {
        if (repository.findByEmail(user.getUsername()).isPresent())
            throw new UserAlreadyExistsException();

        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivated(false);
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.findByName("ROLE_USER"));
        user.setRoles(roles);
        user.addSecretKey();
        User savedUser = repository.save(user);

        VerificationToken verificationToken = verificationTokenService.generateVerificationToken(user);
        emailService.sendEmail(user.getUsername(), "Account verification", "https://localhost:4201/confirm/" + verificationToken.getToken() + " Click on this link to activate your account");
        return savedUser;
    }

    public void updateUserRole(User user, String roleName) {
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.findByName(roleName));
        user.setRoles(roles);
        repository.save(user);
    }

    public void activateUser(User user) {
        user.setActivated(true);
        repository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        return repository.save(user);
    }

    public User changePassword(ChangePasswordDTO changePasswordDTO) throws UserNotFoundException, WrongPasswordException {
        Optional<User> user = findByEmail(changePasswordDTO.getEmail());
        if (user.isEmpty())
            throw new UserNotFoundException();
        if (!BCrypt.checkpw(changePasswordDTO.getOldPassword(), user.get().getPassword()))
            throw new WrongPasswordException();
        return changePassword(user.get(), changePasswordDTO.getNewPassword());
    }

    public String change2FAStatus(String userId, boolean enableFA) {
        User user = findById(userId).orElseThrow(UserNotFoundException::new);
        user.setUsing2FA(enableFA);
        repository.save(user);
        return enableFA ? user.getSecret() : "";
    }
}
