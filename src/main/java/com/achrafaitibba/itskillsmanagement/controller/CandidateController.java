package com.achrafaitibba.itskillsmanagement.controller;

import com.achrafaitibba.itskillsmanagement.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/candidate")
@RequiredArgsConstructor
public class CandidateController {
    private final CandidateService candidateService;

    @PostMapping("/uploadResume")
    public String addResume(@RequestBody String url){
        return candidateService.uploadResume(url);
    }


    //todo, add/delete skill
}
