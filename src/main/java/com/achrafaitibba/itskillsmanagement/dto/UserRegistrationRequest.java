package com.achrafaitibba.itskillsmanagement.dto;

public record UserRegistrationRequest(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String address,
        String role,
        String password

) {
}
