package com.agent.repository;

import com.agent.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
    @Query("SELECT c FROM Company c WHERE c.activated = :activated")
    List<Company> getCompanies(@Param("activated") boolean activated);
    
    Company findByOwnerId(String userId);
}
