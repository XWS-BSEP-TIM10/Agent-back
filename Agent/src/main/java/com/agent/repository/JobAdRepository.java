package com.agent.repository;

import com.agent.model.JobAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobAdRepository extends JpaRepository<JobAd, String> {
    List<JobAd> findAllByCompanyId(String id);
}
