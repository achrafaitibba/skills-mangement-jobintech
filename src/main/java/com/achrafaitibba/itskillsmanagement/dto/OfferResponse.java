package com.achrafaitibba.itskillsmanagement.dto;

public record OfferResponse(
        Long id,
        String title,
        String description,
        Long salary,
        String location,

        String companyName
) {
}
