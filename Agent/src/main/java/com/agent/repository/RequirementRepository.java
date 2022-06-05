package com.agent.repository;

import com.agent.model.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequirementRepository extends JpaRepository<Requirement, String> {
    Optional<Requirement> findByName(String name);
}
