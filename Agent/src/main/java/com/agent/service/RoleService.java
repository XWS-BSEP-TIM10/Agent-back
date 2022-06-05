package com.agent.service;

import com.agent.model.Role;
import com.agent.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Role findByName(String name) {
        return repository.findByName(name);
    }
}
