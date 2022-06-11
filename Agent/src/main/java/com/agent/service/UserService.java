package com.agent.service;

import com.agent.exception.UserAlreadyExistsException;
import com.agent.model.Role;
import com.agent.model.User;
import com.agent.model.VerificationToken;
import com.agent.repository.UserRepository;
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
        if (repository.findByEmail(user.getEmail()).isPresent())
            throw new UserAlreadyExistsException();
        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivated(false);
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.findByName("ROLE_USER"));
        user.setRoles(roles);
        User savedUser = repository.save(user);

        VerificationToken verificationToken = verificationTokenService.generateVerificationToken(user);
        emailService.sendEmail(user.getEmail(), "Account verification", "https://localhost:4201/confirm/" + verificationToken.getToken() + " Click on this link to activate your account");
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
}
