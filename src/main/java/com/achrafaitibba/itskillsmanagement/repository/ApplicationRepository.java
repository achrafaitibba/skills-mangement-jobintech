package com.achrafaitibba.itskillsmanagement.repository;

import com.achrafaitibba.itskillsmanagement.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
