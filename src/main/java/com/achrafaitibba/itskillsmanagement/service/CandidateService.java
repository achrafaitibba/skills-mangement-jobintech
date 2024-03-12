package com.achrafaitibba.itskillsmanagement.service;

import com.achrafaitibba.itskillsmanagement.configuration.token.JwtService;
import com.achrafaitibba.itskillsmanagement.dto.SkillRequest;
import com.achrafaitibba.itskillsmanagement.dto.SkillResponse;
import com.achrafaitibba.itskillsmanagement.enums.Level;
import com.achrafaitibba.itskillsmanagement.model.Candidate;
import com.achrafaitibba.itskillsmanagement.model.Skill;
import com.achrafaitibba.itskillsmanagement.model.User;
import com.achrafaitibba.itskillsmanagement.repository.CandidateRepository;
import com.achrafaitibba.itskillsmanagement.repository.SkillRepository;
import com.achrafaitibba.itskillsmanagement.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final HttpServletRequest httpServletRequest;
    private final SkillRepository skillRepository;

    public String uploadResume(String URL) {
        String header = httpServletRequest.getHeader("Authorization");
        String jwt = header.substring(7);
        Claims claims = jwtService.extractAllClaims(jwt);
        String email = claims.getSubject();
        if (userRepository.findByEmail(email).isPresent()) {
            Optional<Candidate> candidate = candidateRepository.findCandidateByUserEmail(email);
            if (candidate.isPresent()) {
                candidate.get().setResume(URL);
                candidateRepository.save(candidate.get());
            } else {

                candidateRepository.save(
                        Candidate.builder()
                                .user(userRepository.findByEmail(email).get())
                                .resume(URL)
                                .build()
                );
            }
        }
        return URL;
    }

    public SkillResponse createSkill(SkillRequest request) {
        String header = httpServletRequest.getHeader("Authorization");
        String jwt = header.substring(7);
        Claims claims = jwtService.extractAllClaims(jwt);
        String email = claims.getSubject();
        Skill saved = skillRepository.save(Skill.builder()
                .level(Level.valueOf(request.level()))
                .technology(request.technology())
                .candidate(candidateRepository.findCandidateByUserEmail(email).get())
                .build());

        return new SkillResponse(
                saved.getId(),
                saved.getTechnology(),
                saved.getLevel().toString()
        );
    }

    public List<SkillResponse> getAllSkills() {
        String header = httpServletRequest.getHeader("Authorization");
        String jwt = header.substring(7);
        Claims claims = jwtService.extractAllClaims(jwt);
        String email = claims.getSubject();

        return skillRepository.findAllByCandidate_User_Email(email).stream().map(
                skill ->
                    new SkillResponse(skill.getId(), skill.getTechnology(), skill.getLevel().toString())

        ).collect(Collectors.toList());
    }
}