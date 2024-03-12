package com.achrafaitibba.itskillsmanagement.service;

import com.achrafaitibba.itskillsmanagement.configuration.token.JwtService;
import com.achrafaitibba.itskillsmanagement.configuration.token.Token;
import com.achrafaitibba.itskillsmanagement.configuration.token.TokenRepository;
import com.achrafaitibba.itskillsmanagement.configuration.token.TokenType;
import com.achrafaitibba.itskillsmanagement.dto.UserRegistrationRequest;
import com.achrafaitibba.itskillsmanagement.dto.UserRegistrationResponse;
import com.achrafaitibba.itskillsmanagement.enums.Role;
import com.achrafaitibba.itskillsmanagement.exception.ApiException;
import com.achrafaitibba.itskillsmanagement.exception.CustomErrorMessage;
import com.achrafaitibba.itskillsmanagement.exception.RequestException;
import com.achrafaitibba.itskillsmanagement.model.User;
import com.achrafaitibba.itskillsmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;


    public UserRegistrationResponse register(UserRegistrationRequest user) {

        if(userRepository.findByEmail(user.email()).isPresent()){
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

        Map<String, Object> claims = new HashMap<>();
        claims.put("role",toSave.getRole());
        var jwtToken = jwtService.generateToken(claims, toSave);
        var refreshToken = jwtService.generateRefreshToken(toSave);
        saveUserToken(toSave, jwtToken);
        return new UserRegistrationResponse(
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

}
