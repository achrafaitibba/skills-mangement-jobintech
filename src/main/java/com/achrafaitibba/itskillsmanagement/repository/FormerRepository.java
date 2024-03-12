package com.achrafaitibba.itskillsmanagement.repository;

import com.achrafaitibba.itskillsmanagement.model.Former;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormerRepository extends JpaRepository<Former, Long> {
}
