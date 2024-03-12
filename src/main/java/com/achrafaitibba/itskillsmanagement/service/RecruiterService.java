package com.achrafaitibba.itskillsmanagement.service;

import com.achrafaitibba.itskillsmanagement.configuration.token.JwtService;
import com.achrafaitibba.itskillsmanagement.enums.Role;
import com.achrafaitibba.itskillsmanagement.exception.CustomErrorMessage;
import com.achrafaitibba.itskillsmanagement.exception.RequestException;
import com.achrafaitibba.itskillsmanagement.model.Recruiter;
import com.achrafaitibba.itskillsmanagement.model.User;
import com.achrafaitibba.itskillsmanagement.repository.RecruiterRepository;
import com.achrafaitibba.itskillsmanagement.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecruiterService {
    private final RecruiterRepository recruiterRepository;
    private final HttpServletRequest httpServletRequest;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public void updateCompanyName(String companyName) {
        String header = httpServletRequest.getHeader("Authorization");
        String jwt = header.substring(7);
        Claims claims = jwtService.extractAllClaims(jwt);
        String email = claims.getSubject();
        String role = userRepository.findByEmail(email).get().getRole().toString();
        if (role.equals(Role.RECRUITER.toString())) {
            Recruiter toSave = recruiterRepository.findByUserEmail(email).get();
            toSave.setCompanyName(companyName);
            recruiterRepository.save(toSave);
        } else {
            throw new RequestException(CustomErrorMessage.NOT_AUTHORIZED.getMessage(), HttpStatus.UNAUTHORIZED);
        }

    }
}
