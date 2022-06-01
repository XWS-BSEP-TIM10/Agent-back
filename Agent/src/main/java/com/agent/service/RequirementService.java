package com.agent.service;

import com.agent.model.Requirement;
import com.agent.repository.RequirementRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RequirementService {
    private final RequirementRepository repo;

    public RequirementService(RequirementRepository repo) {
        this.repo = repo;
    }

    public Optional<Requirement> findByName(String name) {
        return repo.findByName(name);
    }

    public Requirement create(String name) {
        Requirement requirement = new Requirement(UUID.randomUUID().toString(), name);
        return repo.save(requirement);
    }


}
