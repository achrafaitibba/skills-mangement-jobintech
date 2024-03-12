package com.achrafaitibba.itskillsmanagement.dto;

public record UserRegistrationResponse(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String address,
        String role,
        String token,
        String refreshToken
) {
}
