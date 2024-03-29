package com.achrafaitibba.itskillsmanagement.repository;

import com.achrafaitibba.itskillsmanagement.model.Offer;
import com.achrafaitibba.itskillsmanagement.model.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {

    Optional<Recruiter> findByUserEmail(String email);
}
