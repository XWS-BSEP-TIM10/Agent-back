package com.agent.service;

import com.agent.model.APIToken;
import com.agent.model.User;
import com.agent.repository.APITokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class APITokenService {

    private final APITokenRepository repository;

    private final UserService userService;

    public APITokenService(APITokenRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public APIToken add(String apiToken, String userId) {
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) return null;
        Optional<APIToken> existingApiToken = repository.findByUser(user);
        existingApiToken.ifPresent(repository::delete);
        APIToken newApiToken = new APIToken(apiToken, user.get());
        return repository.save(newApiToken);
    }

    public APIToken findByUser(String userId) {
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) return null;
        Optional<APIToken> existingApiToken = repository.findByUser(user);
        if (existingApiToken.isEmpty()) return null;
        else return existingApiToken.get();
    }
}
