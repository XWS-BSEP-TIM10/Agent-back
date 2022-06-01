package com.agent.repository;

import com.agent.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, String> {
    List<Salary> findAllByCompanyId(String id);
}
