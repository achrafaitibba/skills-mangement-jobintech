package com.achrafaitibba.itskillsmanagement.dto;

public record UserAuthenticationRequest(
        String email,
        String password
) {
}
