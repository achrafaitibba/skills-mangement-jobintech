package com.achrafaitibba.itskillsmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ItSkillsManagementApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ItSkillsManagementApplication.class, args);
    }

}
