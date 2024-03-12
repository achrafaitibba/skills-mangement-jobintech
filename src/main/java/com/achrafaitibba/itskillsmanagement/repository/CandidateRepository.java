package com.achrafaitibba.itskillsmanagement.repository;

import com.achrafaitibba.itskillsmanagement.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Optional<Candidate> findCandidateByUserEmail(String email);
}
