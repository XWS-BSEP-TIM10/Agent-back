package com.agent.repository;

import com.agent.model.APIToken;
import com.agent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface APITokenRepository extends JpaRepository<APIToken, Long> {
    Optional<APIToken> findByUser(Optional<User> user);
}
