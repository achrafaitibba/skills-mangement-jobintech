package com.achrafaitibba.itskillsmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Recruiter {
    @Id
    @GeneratedValue
    private Long id;
    private String companyName;
    @OneToOne
    private User user;
    @OneToMany
    private List<Offer> offers;
}
