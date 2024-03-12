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
public class Candidate {
    @Id
    @GeneratedValue
    private Long id;
    private String resume;
    @OneToOne
    private User user;
    @OneToMany
    private List<Skill> skills;
}
