package com.achrafaitibba.itskillsmanagement.service;

import com.achrafaitibba.itskillsmanagement.configuration.token.JwtService;
import com.achrafaitibba.itskillsmanagement.configuration.token.Token;
import com.achrafaitibba.itskillsmanagement.configuration.token.TokenRepository;
import com.achrafaitibba.itskillsmanagement.configuration.token.TokenType;
import com.achrafaitibba.itskillsmanagement.dto.UserAuthenticationRequest;
import com.achrafaitibba.itskillsmanagement.dto.UserRegistrationRequest;
import com.achrafaitibba.itskillsmanagement.dto.UserResponse;
import com.achrafaitibba.itskillsmanagement.enums.Role;
import com.achrafaitibba.itskillsmanagement.exception.ApiException;
import com.achrafaitibba.itskillsmanagement.exception.CustomErrorMessage;
import com.achrafaitibba.itskillsmanagement.exception.RequestException;
import com.achrafaitibba.itskillsmanagement.model.Candidate;
import com.achrafaitibba.itskillsmanagement.model.Former;
import com.achrafaitibba.itskillsmanagement.model.Recruiter;
import com.achrafaitibba.itskillsmanagement.model.User;
import com.achrafaitibba.itskillsmanagement.repository.CandidateRepository;
import com.achrafaitibba.itskillsmanagement.repository.FormerRepository;
import com.achrafaitibba.itskillsmanagement.repository.RecruiterRepository;
import com.achrafaitibba.itskillsmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.achrafaitibba.itskillsmanagement.exception.CustomErrorMessage.ACCOUNT_NOT_EXIST;
import static com.achrafaitibba.itskillsmanagement.exception.CustomErrorMessage.PASSWORD_INCORRECT;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final CandidateRepository candidateRepository;
    private final FormerRepository formerRepository;
    private final RecruiterRepository recruiterRepository;

    public UserResponse register(UserRegistrationRequest user) {

        if (userRepository.findByEmail(user.email()).isPresent()) {
            throw new RequestException(CustomErrorMessage.ACCOUNT_ALREADY_EXIST.getMessage(), HttpStatus.CONFLICT);
        }
        User toSave = userRepository.save(User.builder()
                .email(user.email())
                .firstName(user.firstName())
                .lastName(user.lastName())
                .address(user.address())
                .phoneNumber(user.phoneNumber())
                .role(Role.valueOf(user.role()))
                .password(passwordEncoder.encode(user.password()))
                .build());

        switch (user.role()) {
            case "CANDIDATE": {
                Candidate candidate = Candidate.builder()
                        .user(toSave)
                        .build();
                candidateRepository.save(candidate);
                break;
            }
            case "FORMER": {
                Former former = Former.builder()
                        .user(toSave)
                        .build();
                formerRepository.save(former);
                break;
            }
            case "RECRUITER": {
                Recruiter recruiter = Recruiter.builder()
                        .user(toSave)
                        .build();
                recruiterRepository.save(recruiter);
                break;
            }
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", toSave.getRole());
        var jwtToken = jwtService.generateToken(claims, toSave);
        var refreshToken = jwtService.generateRefreshToken(toSave);
        saveUserToken(toSave, jwtToken);
        return new UserResponse(
                toSave.getFirstName(),
                toSave.getLastName(),
                toSave.getEmail(),
                toSave.getPhoneNumber(),
                toSave.getAddress(),
                toSave.getRole().toString(),
                jwtToken,
                refreshToken);
    }


    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public UserResponse authenticate(UserAuthenticationRequest request) {
        Optional<User> toAuthenticate = userRepository.findByEmail(request.email());
        if (!toAuthenticate.isPresent()) {
            throw new RequestException(ACCOUNT_NOT_EXIST.getMessage(), HttpStatus.NOT_FOUND);
        } else if (!passwordEncoder.matches(request.password(), toAuthenticate.get().getPassword())) {
            throw new RequestException(PASSWORD_INCORRECT.getMessage(), HttpStatus.NOT_FOUND);
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var jwtToken = jwtService.generateToken(new HashMap<>(), toAuthenticate.get());
        var refreshToken = jwtService.generateRefreshToken(toAuthenticate.get());
        saveUserToken(userRepository.findByEmail(request.email()).get(), jwtToken);
        return new UserResponse(
                toAuthenticate.get().getFirstName(),
                toAuthenticate.get().getLastName(),
                request.email(),
                toAuthenticate.get().getPhoneNumber(),
                toAuthenticate.get().getAddress(),
                toAuthenticate.get().getRole().toString(),
                jwtToken,
                refreshToken);


    }
}
