package com.achrafaitibba.itskillsmanagement.controller;

import com.achrafaitibba.itskillsmanagement.repository.RecruiterRepository;
import com.achrafaitibba.itskillsmanagement.service.RecruiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recruiter")
@RequiredArgsConstructor
public class RecruiterController {

    private final RecruiterService recruiterService;

    @PostMapping("/companyName")
    public ResponseEntity<Void> updateCompanyName(@RequestBody String companyName) {
        recruiterService.updateCompanyName(companyName);
        return null;
    }
}
