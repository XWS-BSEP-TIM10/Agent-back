package com.agent.service;


import com.agent.model.User;
import com.agent.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    Optional<User> findById(String id) {
        return repository.findById(id);
    }
}
