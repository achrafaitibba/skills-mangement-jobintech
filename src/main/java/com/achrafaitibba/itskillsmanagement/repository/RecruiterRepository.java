package com.achrafaitibba.itskillsmanagement.repository;

import com.achrafaitibba.itskillsmanagement.model.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {
}
