package com.agent.service;

import com.agent.exception.UserAlreadyExistsException;
import com.agent.model.Role;
import com.agent.model.User;
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

    public UserService(UserRepository repository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findById(String id) {
        return repository.findById(id);
    }


    public User addNewUser(User user) throws UserAlreadyExistsException {
        if (repository.findByEmail(user.getEmail()).isPresent())
            throw new UserAlreadyExistsException();
        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivated(true);
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.findByName("ROLE_USER"));
        user.setRoles(roles);
        return repository.save(user);
    }

    public void updateUserRole(User user, String roleName) {
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.findByName(roleName));
        user.setRoles(roles);
        repository.save(user);
    }
}
