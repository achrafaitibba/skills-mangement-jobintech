package com.achrafaitibba.itskillsmanagement.controller;

import com.achrafaitibba.itskillsmanagement.dto.SkillRequest;
import com.achrafaitibba.itskillsmanagement.dto.SkillResponse;
import com.achrafaitibba.itskillsmanagement.model.Skill;
import com.achrafaitibba.itskillsmanagement.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/candidate")
@RequiredArgsConstructor
public class CandidateController {
    private final CandidateService candidateService;

    @PostMapping("/uploadResume")
    public String addResume(@RequestBody String url){
        return candidateService.uploadResume(url);
    }


    @PostMapping("/skills/create")
    public SkillResponse createSkill(@RequestBody SkillRequest request){
        return candidateService.createSkill(request);
    }

    @GetMapping("/skills")
    public List<SkillResponse> getAllSkillsByCandidate(){
        return candidateService.getAllSkills();
    }
}
