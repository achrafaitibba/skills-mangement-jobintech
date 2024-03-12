package com.achrafaitibba.itskillsmanagement.service;

import com.achrafaitibba.itskillsmanagement.configuration.token.JwtService;
import com.achrafaitibba.itskillsmanagement.model.Candidate;
import com.achrafaitibba.itskillsmanagement.model.User;
import com.achrafaitibba.itskillsmanagement.repository.CandidateRepository;
import com.achrafaitibba.itskillsmanagement.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final HttpServletRequest httpServletRequest;
    public String uploadResume(String URL){
        String header = httpServletRequest.getHeader("Authorization");
        String jwt = header.substring(7);
        Claims claims = jwtService.extractAllClaims(jwt);
        String email = claims.getSubject();
        if(userRepository.findByEmail(email).isPresent()){
            Optional<Candidate> candidate = candidateRepository.findCandidateByUserEmail(email);
            if(candidate.isPresent()){
                candidate.get().setResume(URL);
                candidateRepository.save(candidate.get());
            }else {

                candidateRepository.save(
                        Candidate.builder()
                                .user((User) userRepository.findByEmail(email).get())
                                .resume(URL)
                                .build()
                );
            }
        }
        return URL;
    }
}
